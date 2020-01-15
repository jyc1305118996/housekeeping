package com.haige.service.impl;

import com.alibaba.fastjson.JSON;
import com.haige.common.bean.IdWorker;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.OrderStatusEnum;
import com.haige.common.enums.ServiceOrderStatusEnum;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.*;
import com.haige.db.mapper.*;
import com.haige.integration.SmsClient;
import com.haige.integration.WXPayService;
import com.haige.integration.param.SendMessageParam;
import com.haige.service.OrderService;
import com.haige.service.UserBaseService;
import com.haige.service.convert.OrderConvertUtils;
import com.haige.service.convert.ServiceOrderDetailConvertUtils;
import com.haige.service.convert.ShortMsgConvertUtils;
import com.haige.service.dto.*;
import com.haige.util.DateUtils;
import com.haige.util.OrderUtils;
import com.haige.util.TimeUtil;
import com.haige.util.wxUtil.WXPayUtil;
import com.haige.web.vo.OrderDetailVO;
import com.haige.web.vo.PayVO;
import com.haige.web.vo.SubmitOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Archie
 * @date 2019/11/10 13:58
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {

    /**
     * 订单表
     */
    @Autowired
    private OrderDOMapper orderDOMapper;

    /**
     * 套餐表
     */
    @Autowired
    private GoodsInfoDOMapper goodsInfoDOMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserBaseDOMapper userBaseDOMapper;

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ServeDetailDOMapper serveDetailDOMapper;
    @Autowired
    private CouponDOMapper couponDOMapper;

    @Value("${wx.mchId}")
    private String mchId;

    @Value("${wx.apikey}")
    private String appkey;

    @Override
    public Mono<ResultInfo<SubmitOrderVo>> submit(
            ServerWebExchange serverWebExchange, Mono<SubmitOrderDTO> orderRequestMono) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> tokens = request.getHeaders().get("Authorization");
        String ip = request.getRemoteAddress().toString();
        UserBaseDO userBaseDO = userBaseDOMapper.findByToken(tokens.get(0));
        return orderRequestMono
                .map(
                        submitOrderDTO -> {
                            // 查询套餐金额,查询优惠券金额,计算出总金额并返回
                            GoodsInfoDO goodsInfoDO =
                                    goodsInfoDOMapper.selectByPrimaryKey(submitOrderDTO.getGoodsId());
                            if (submitOrderDTO.getGoodsId() == 1
                                    && submitOrderDTO.getCouponIds() != null
                                    && submitOrderDTO.getCouponIds().length != 0) {
                                throw new RuntimeException("静居单次不能使用优惠卷");
                            }
                            // 套餐金额
                            BigDecimal goodsPrice = goodsInfoDO.getGoodsPrice();
                            // 生成订单
                            OrderDO orderDO = new OrderDO();
                            orderDO.setOrderAddress(submitOrderDTO.getAddress());
                            orderDO.setOrderCount(goodsInfoDO.getGoodsFreq());
                            orderDO.setOrderPrice(goodsPrice);
                            orderDO.setOrderAmount(goodsPrice);
                            // 判断优惠卷
                            if (submitOrderDTO.getCouponIds() != null
                                    && submitOrderDTO.getCouponIds().length > 0) {
                                BigDecimal money = new BigDecimal(0);
                                // 查询优惠券并且计算总金额
                                Double reduce =
                                        Arrays.stream(submitOrderDTO.getCouponIds())
                                                .map(id -> couponDOMapper.selectByPrimaryKey(id))
                                                .peek(
                                                        couponDO -> {
                                                            // 修改为已使用
                                                            couponDO.setUcIsUse("1");
                                                            orderDO.setCouponIds(String.valueOf(couponDO.getUcId()));
                                                            couponDOMapper.updateByPrimaryKeySelective(couponDO);
                                                        })
                                                .map(CouponDO::getUcCouponPrice)
                                                .map(BigDecimal::doubleValue)
                                                .reduce(0.0, (price1, price2) -> price1 + price2);
                                BigDecimal divide = goodsPrice.subtract(new BigDecimal(reduce));
                                orderDO.setOrderAmount(divide);
                            }
                            orderDO.setOrderId("DDH" + String.valueOf(idWorker.nextId()));
                            orderDO.setGoodsId(submitOrderDTO.getGoodsId());
                            orderDO.setGoodsName(goodsInfoDO.getGoodsName());
                            orderDO.setOrderCreateTime(new Date());
                            orderDO.setOrderCreateUser(userBaseDO.getUbdId());
                            orderDO.setOrderStatus(OrderStatusEnum.NON_PAYMENT.getOrderStatus());
                            orderDO.setOrderUpdateTime(new Date());
                            orderDO.setOrderUpdateUser(userBaseDO.getUbdId());
                            orderDOMapper.insertSelective(orderDO);
                            return orderDO;
                        })
                .map(
                        orderDO -> {
                            SubmitOrderVo submitOrderVo = OrderConvertUtils.toVO(orderDO);
                            return ResultInfo.buildSuccess(submitOrderVo);
                        });
    }

    @Override
    public Mono<ResultInfo<List<OrderDetailVO>>> queryOrderListByCondition(
            ServerWebExchange serverWebExchange, int status) {

        // status ==0 则查询所有
        // 否则按照状态来
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> auth = request.getHeaders().get("Authorization");
        UserBaseDTO userBaseDTO = userBaseService.findByToken(auth.get(0));
        // 获取用户权限
        // 管理员查询全部
        //
        int userAdmin = userBaseDTO.getUbdAdmin();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", String.valueOf(status));

        if (userAdmin == 0) {
            hashMap.put("userid", "0");

        } else {

            hashMap.put("userid", userBaseDTO.getUbdId().toString()); // 非管理员查询自己的
        }

        List<OrderDO> orderDOList = orderDOMapper.findOrderDoList(hashMap);

        List<OrderDetailVO> orderDetailVOList = new ArrayList<>(orderDOList.size());

        for (int i = 0; i < orderDOList.size(); i++) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();

            orderDetailVO.setGoodsId(orderDOList.get(i).getGoodsId().toString());
            orderDetailVO.setAmount(orderDOList.get(i).getOrderAmount());
            orderDetailVO.setOrderId(orderDOList.get(i).getOrderId());

            orderDetailVO.setGoodsName(orderDOList.get(i).getGoodsName());

            orderDetailVO.setPrice(orderDOList.get(i).getOrderPrice());

            orderDetailVO.setTime(orderDOList.get(i).getOrderCreateTime());

            orderDetailVO.setFiles(orderDOList.get(i).getFiles());

            orderDetailVO.setAddress(orderDOList.get(i).getOrderAddress());

            orderDetailVO.setNumber(orderDOList.get(i).getOrderCount().toString());

            orderDetailVO.setFiles(orderDOList.get(i).getFiles());
            orderDetailVOList.add(orderDetailVO);
        }

        ResultInfo<List<OrderDetailVO>> result = new ResultInfo<List<OrderDetailVO>>();
        result.setData(orderDetailVOList);
        result.setCount(Long.valueOf(orderDOList.size()));
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<ResultInfo> pay(ServerWebExchange exchange, Mono<PayDTO> payDTOMono) {
        Mono<String> orderParamMono =
                payDTOMono.map(
                        payDTO -> {
                            // 查询订单。封装微信支付参数
                            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(payDTO.getOrderId());
                            HashMap<String, String> data = new HashMap<>();
                            // 小程序身份证
                            data.put("appid", payDTO.getAppid());
                            // 商户号
                            data.put("mch_id", mchId);
                            // 设备号
                            data.put("device_info", "app");
                            // 随机字符串  包含数据和字母
                            String nonceStr = RandomStringUtils.random(32, true, true);
                            data.put("nonce_str", nonceStr);
                            // 商品名
                            data.put("body", orderDO.getGoodsName());
                            // 商户订单号
                            data.put("out_trade_no", orderDO.getOrderId());
                            String totalFee =
                                    String.valueOf(((int) (orderDO.getOrderAmount().doubleValue() * 100)));
                            data.put("total_fee", totalFee);
                            // 终端ip
                            String ip = exchange.getRequest().getHeaders().get("X-Real-IP").get(0);
                            data.put("spbill_create_ip", ip);
                            // todo 微信成功回调地址
                            data.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
                            // 交易类型
                            data.put("trade_type", "JSAPI");
                            // 查询用户
                            String wxID =
                                    userBaseDOMapper
                                            .selectByPrimaryKey(orderDO.getOrderCreateUser())
                                            .getUbdWechatId();
                            data.put("openid", wxID);
                            // 按照k得首字母排序
                            Map<String, String> collect =
                                    data.entrySet().stream()
                                            .sorted(Comparator.comparing(entry -> entry.getKey().substring(0, 1)))
                                            .collect(
                                                    Collectors.toMap(
                                                            Map.Entry::getKey,
                                                            Map.Entry::getValue,
                                                            (a, b) -> b,
                                                            LinkedHashMap::new));
                            // 计算签名
                            String xmlParam = null;
                            try {
                                xmlParam = WXPayUtil.generateSignedXml(collect, appkey);
                            } catch (Exception e) {
                                log.error("小程序签名生成失败:{}", e.getMessage(), e);
                                throw new RuntimeException("小程序签名生成失败:{}", e);
                            }
                            return xmlParam;
                        });
        // 发起预支付
        return wxPayService
                .submitOrder(orderParamMono)
                .map(
                        submitOrderResult -> {
                            PayVO payVO = new PayVO();
                            payVO.setPrepayId(submitOrderResult.getPrepayId());
                            return ResultInfo.buildSuccess(payVO);
                        });
    }

    @Override
    public Mono<ResultInfo> updateOrder(
            ServerWebExchange exchange, Mono<UpdateOrderDTO> orderRequestMono) {
        ServerHttpRequest request = exchange.getRequest();
        return orderRequestMono
                .map(
                        updateOrderDTO -> {
                            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(updateOrderDTO.getOrderId());
                            orderDO.setOrderStatus(updateOrderDTO.getOrderStatus());
                            orderDOMapper.updateByPrimaryKeySelective(orderDO);
                            return orderDO;
                        })
                .map(
                        orderDO -> {
                            if ("100".equals(orderDO.getOrderStatus())) {
                                // 发送下单成功通知短信
                                List<String> auth = request.getHeaders().get("Authorization");
                                UserBaseDO userBaseDO = userBaseDOMapper.findByToken(auth.get(0));
                                if ("1".equals(userBaseDO.getUbdIsNew())) {
                                    // 更新非新用户
                                    userBaseDO.setUbdIsNew("0");
                                    userBaseDOMapper.updateByPrimaryKeySelective(userBaseDO);
                                }
                                // 付款成功发送短信
                                HashMap<String, String> param = new HashMap<>();
                                param.put("goods", orderDO.getGoodsName());
                                param.put("orderid", OrderUtils.subOrderId(orderDO.getOrderId(), 3));
                                // 获取模板
                                SendMessageParam.SmsTemplate payOKTemplate =
                                        ShortMsgConvertUtils.getPayOKTemplate(param);
                                SendMessageParam sendMessageParam = new SendMessageParam();
                                sendMessageParam.setSmsTemplate(payOKTemplate);
                                sendMessageParam.setIphone(userBaseDO.getUbdFixedPhone());
                                sendMessageParam.setType("下单");
                                smsClient.sendMessage(sendMessageParam);
                            } else if ("400".equals(orderDO.getOrderStatus())) {
                                // 优惠卷撤回
                                String couponIds = orderDO.getCouponIds();
                                if (couponIds != null) {
                                    CouponDO couponDO =
                                            couponDOMapper.selectByPrimaryKey(Integer.parseInt(couponIds));
                                    couponDO.setUcIsUse("0");
                                    couponDOMapper.updateByPrimaryKeySelective(couponDO);
                                }
                            }
                            return "SUCCESS";
                        })
                .map(ResultInfo::buildSuccess);
    }

    /**
     * @param userBaseDTO  当前登陆人
     * @param allotDTOMono 分配信息
     * @return
     */
    @Override
    public Mono<ResultInfo> allot(UserBaseDTO userBaseDTO, Mono<AllotDTO> allotDTOMono) {
        return allotDTOMono
                .map(
                        allot -> {
                            ServeDetailDO serveDetailDO =
                                    serveDetailDOMapper.selectByPrimaryKey(allot.getServiceId());
                            serveDetailDO.setServeUserId(allot.getUserId());
                            serveDetailDO.setServeCreateUser(userBaseDTO.getUbdId());
                            serveDetailDO.setServeUpdateTime(new Date());
                            serveDetailDOMapper.updateByPrimaryKeySelective(serveDetailDO);
                            return Tuples.of(allot, serveDetailDO);
                        })
                .map(
                        tuple2 -> {
                            // 查询服务人员信息
                            UserBaseDO waiter = userBaseDOMapper.selectByPrimaryKey(tuple2.getT1().getUserId());
                            // 查询客户信息
                            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(tuple2.getT2().getOrderId());

                            // 发短息通知客户，已分配人员；
                            HashMap<String, String> acceptReservationsParam = new HashMap<>();
                            acceptReservationsParam.put("phone", waiter.getUbdFixedPhone());
                            acceptReservationsParam.put(
                                    "date", DateUtils.dateToString(tuple2.getT2().getServeStartTime()));
                            SendMessageParam.SmsTemplate acceptReservations =
                                    ShortMsgConvertUtils.getAcceptReservations(acceptReservationsParam);
                            SendMessageParam sendMessageParam = new SendMessageParam();
                            // 客户手机号
                            sendMessageParam.setIphone(tuple2.getT2().getConcatIphone());
                            sendMessageParam.setSmsTemplate(acceptReservations);
                            sendMessageParam.setType("预约成功通知");
                            smsClient.sendMessage(sendMessageParam);

                            // 通知服务人员尽快服务
                            HashMap<String, String> assignedPersonnelParam = new HashMap<>();
                            assignedPersonnelParam.put("orderid", OrderUtils.subOrderId(orderDO.getOrderId(), 3));
                            assignedPersonnelParam.put("name", tuple2.getT2().getConcatName());
                            assignedPersonnelParam.put("number", tuple2.getT2().getConcatIphone());
                            SendMessageParam.SmsTemplate assignedPersonnel =
                                    ShortMsgConvertUtils.getAssignedPersonnel(assignedPersonnelParam);
                            SendMessageParam sendMessageParam1 = new SendMessageParam();
                            // 服务员手机号
                            sendMessageParam1.setIphone(waiter.getUbdFixedPhone());
                            sendMessageParam1.setSmsTemplate(assignedPersonnel);
                            sendMessageParam1.setType("分配任务通知");
                            smsClient.sendMessage(sendMessageParam1);
                            return "SUCCESS";
                        })
                .map(ResultInfo::buildSuccess);
    }

    @Override
    public Mono<ResultInfo> countOrder(
            ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> auth = request.getHeaders().get("Authorization");
        UserBaseDTO userBaseDTO = userBaseService.findByToken(auth.get(0));
        // 获取用户权限
        // 管理员查询全部
        //
        int userAdmin = userBaseDTO.getUbdAdmin();
        // hashMap.put("status", String.valueOf(status));

        String userId = userBaseDTO.getUbdId().toString();
        if (userAdmin == 0) {
            userId = null;
        }
        List<HashMap<String, String>> orderDOList = orderDOMapper.countOrder(userId);
        return Flux.just(ServiceOrderStatusEnum.values())
                .map(ServiceOrderStatusEnum::getStatus)
                .concatWith(Flux.just(OrderStatusEnum.values())
                        .map(OrderStatusEnum::getOrderStatus))
                .map(status -> {
                    HashMap<String, String> map = new HashMap<>(1);
                    map.put("ct", "0");
                    map.put("order_status", status);
                    return map;
                })
                .doOnNext(map -> {
                    orderDOList.stream()
                            .filter(hashMap -> map.get("order_status").equals(hashMap.get("order_status")))
                            .forEach(hashMap ->map.put("ct", hashMap.get("ct")));
                })
                .collect(Collectors.toList())
                .map(ResultInfo::buildSuccess);
    }

    /**
     * @description: 根据服务状态查询订单
     * @param:
     * @return: 返回订单集合
     * @author: huxianming
     * @date: 2019-11-12
     */
    @Override
    public Mono<ResultInfo<List<OrderDetailVO>>> queryOrderListByDetailsStatus(
            ServerWebExchange serverWebExchange, int detailsStatus) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> auth = request.getHeaders().get("Authorization");
        UserBaseDTO userBaseDTO = userBaseService.findByToken(auth.get(0));
        // 获取用户权限
        // 管理员查询全部
        //
        int userAdmin = userBaseDTO.getUbdAdmin();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", String.valueOf(detailsStatus));
        if (userAdmin == 0) {
            hashMap.put("userid", "0");

        } else {

            hashMap.put("userid", userBaseDTO.getUbdId().toString()); // 非管理员查询自己的
        }
        List<HashMap<String, String>> serveDetailDOList =
                serveDetailDOMapper.findServeDetailDOList(hashMap);
        List<OrderDetailVO> orderDetailVOList = new ArrayList<>(serveDetailDOList.size());

        for (int i = 0; i < serveDetailDOList.size(); i++) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetailVO.setServeId(serveDetailDOList.get(i).get("serve_id"));
            orderDetailVO.setGoodsId(String.valueOf(serveDetailDOList.get(i).get("goods_id")));

            orderDetailVO.setOrderId(serveDetailDOList.get(i).get("order_id"));

            orderDetailVO.setGoodsName(serveDetailDOList.get(i).get("goods_name"));

            orderDetailVO.setPrice(
                    new BigDecimal(String.valueOf(serveDetailDOList.get(i).get("order_amount"))));

            orderDetailVO.setStatus(Integer.valueOf(serveDetailDOList.get(i).get("serve_status")));

            orderDetailVO.setNumber(String.valueOf(serveDetailDOList.get(i).get("order_count")));

            List<FileInfoDO> list = new ArrayList();
            FileInfoDO fileInfoDO = new FileInfoDO();
            fileInfoDO.setFilePath(serveDetailDOList.get(i).get("file_path"));
            list.add(fileInfoDO);

            orderDetailVO.setFiles(list);
            orderDetailVO.setAddress(serveDetailDOList.get(i).get("concat_address"));
            orderDetailVO.setTime(
                    TimeUtil.strToDate(String.valueOf(serveDetailDOList.get(i).get("serve_start_time"))));
            orderDetailVOList.add(orderDetailVO);
        }

        ResultInfo<List<OrderDetailVO>> result = new ResultInfo<List<OrderDetailVO>>();
        result.setData(orderDetailVOList);
        result.setCount(Long.valueOf(orderDetailVOList.size()));
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);
    }


    private List<Map<String, String>> defaultStatus() {
        List<Map<String, String>> list = new ArrayList<>();
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            HashMap<String, String> map = new HashMap<>(1);

            map.put("ct", "0");
            map.put("order_status", orderStatusEnum.getOrderStatus());
            list.add(map);

        }

        for (ServiceOrderStatusEnum serviceOrderStatusEnum : ServiceOrderStatusEnum.values()) {
            HashMap<String, String> map = new HashMap<>(1);
            map.put("ct", "0");
            map.put("order_status", serviceOrderStatusEnum.getStatus());
            list.add(map);
        }

        return list;
    }

    public static void main(String[] args) {
        //

        BigDecimal a = new BigDecimal(111);
        BigDecimal add = a.add(new BigDecimal(1000));
        System.out.println(add);

        System.out.println(OrderStatusEnum.values());
    }
}
