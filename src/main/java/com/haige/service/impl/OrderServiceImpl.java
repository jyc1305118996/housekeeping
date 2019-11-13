package com.haige.service.impl;

import com.alibaba.fastjson.JSON;
import com.haige.common.bean.IdWorker;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.OrderStatusEnum;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.GoodsCouponDO;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.entity.OrderDO;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.GoodsCouponDOMapper;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.integration.WXPayService;
import com.haige.service.OrderService;
import com.haige.service.UserBaseService;
import com.haige.service.convert.OrderConvertUtils;
import com.haige.service.dto.SubmitOrderDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.vo.SubmitOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Archie
 * @date 2019/11/10 13:58
 */
@Service
@Slf4j
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
    /**
     * 优惠券表
     */
    @Autowired
    private GoodsCouponDOMapper goodsCouponDOMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserBaseDOMapper userBaseDOMapper;

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private UserBaseService userBaseService;
    @Override
    public Mono<ResultInfo<SubmitOrderVo>> submit(ServerWebExchange serverWebExchange, Mono<SubmitOrderDTO> orderRequestMono) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> tokens = request.getHeaders().get("Authorization");
        String ip = request.getRemoteAddress().toString();
        UserBaseDO userBaseDO = userBaseDOMapper.findByToken(tokens.get(0));
        return orderRequestMono
                .map(submitOrderDTO -> {
                    // 查询套餐金额,查询优惠券金额,计算出总金额并返回
                    GoodsInfoDO goodsInfoDO = goodsInfoDOMapper.selectByPrimaryKey(submitOrderDTO.getGoodsId());
                    // 套餐金额
                    BigDecimal goodsPrice = goodsInfoDO.getGoodsPrice();
                    // 生成订单
                    OrderDO orderDO = new OrderDO();
                    orderDO.setOrderPrice(goodsPrice);
                    orderDO.setOrderAmount(goodsPrice);
                    // 判断优惠卷
                    if (submitOrderDTO.getCouponIds() != null && submitOrderDTO.getCouponIds().length > 0) {
                        BigDecimal money = new BigDecimal(0);
                        // 查询优惠券并且计算总金额
                        Arrays.stream(submitOrderDTO.getCouponIds())
                                .map(id -> goodsCouponDOMapper.selectByPrimaryKey(id))
                                .map(GoodsCouponDO::getGcPrice)
                                .forEach(money::add);
                        BigDecimal divide = goodsPrice.divide(money);
                        orderDO.setOrderAmount(divide);
                        orderDO.setCouponIds(JSON.toJSONString(submitOrderDTO.getCouponIds()));
                    }
                    orderDO.setOrderId(String.valueOf(idWorker.nextId()));
                    orderDO.setGoodsId(submitOrderDTO.getGoodsId());
                    orderDO.setGoodsName(goodsInfoDO.getGoodsName());
                    orderDO.setOrderCreateTime(new Date());
                    orderDO.setOrderCreateUser(userBaseDO.getUbdId());
                    orderDO.setOrderStatus(OrderStatusEnum.NON_PAYMENT.getOrderStatus());
                    orderDO.setOrderUpdateTime(new Date());
                    orderDO.setOrderUpdateUser(userBaseDO.getUbdId());
                    orderDO.setOrderAmount(goodsPrice);
                    orderDOMapper.insertSelective(orderDO);
                    return orderDO;
                })
                .map(orderDO -> {
                    SubmitOrderVo submitOrderVo = OrderConvertUtils.toVO(orderDO);
                    return ResultInfo.buildSuccess(submitOrderVo);
                });
    }

    @Override
    public Mono<ResultInfo<List<OrderDO>>> queryOrderListByCondition(ServerWebExchange serverWebExchange, int status) {

        //status ==0 则查询所有
        //否则按照状态来
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> auth = request.getHeaders().get("Authorization");
        UserBaseDTO userBaseDTO= userBaseService.findByToken(auth.get(0));
        //获取用户权限
        //管理员查询全部
        //
        int userAdmin = userBaseDTO.getUbdAdmin();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("status",String.valueOf(status));

        if(userAdmin == 0){
            hashMap.put("userid","0");

        }else{

            hashMap.put("userid",userBaseDTO.getUbdId().toString());//非管理员查询自己的
        }

        List<OrderDO> orderDOList = orderDOMapper.findOrderDoList(hashMap);
        ResultInfo<List<OrderDO>> result = new ResultInfo<List<OrderDO>>();
        result.setData(orderDOList);
        result.setCount(String.valueOf(orderDOList.size()));
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);

    }
}
