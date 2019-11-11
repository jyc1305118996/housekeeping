package com.haige.service.impl;

import com.haige.common.bean.IdWorker;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.OrderStatusEnum;
import com.haige.db.entity.GoodsCouponDO;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.entity.OrderDO;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.GoodsCouponDOMapper;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.integration.WXPayService;
import com.haige.integration.param.SubmitOrderParam;
import com.haige.service.OrderService;
import com.haige.service.dto.SubmitOrderDTO;
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

    @Override
    public Mono<ResultInfo<SubmitOrderVo>> submit(ServerWebExchange serverWebExchange, Mono<SubmitOrderDTO> orderRequestMono) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> tokens = request.getHeaders().get("Authorization");
        String ip = request.getRemoteAddress().toString();
        UserBaseDO userBaseDO = userBaseDOMapper.findByToken(tokens.get(0));
        Mono<SubmitOrderParam> param = orderRequestMono
                .map(submitOrderDTO -> {
                    // 查询套餐金额,查询优惠券金额,计算出总金额并返回
                    GoodsInfoDO goodsInfoDO = goodsInfoDOMapper.selectByPrimaryKey(submitOrderDTO.getGoodsId());
                    // 套餐金额
                    BigDecimal goodsPrice = goodsInfoDO.getGoodsPrice();
                    // 判断优惠卷
                    if (submitOrderDTO.getCouponIds() != null && submitOrderDTO.getCouponIds().length > 0) {
                        BigDecimal money = new BigDecimal(0);
                        // 查询优惠券并且计算总金额
                        Arrays.stream(submitOrderDTO.getCouponIds())
                                .map(id -> goodsCouponDOMapper.selectByPrimaryKey(id))
                                .map(GoodsCouponDO::getGcPrice)
                                .forEach(money::add);
                        goodsPrice.divide(money);
                    }
                    // 生成订单
                    OrderDO orderDO = new OrderDO();
                    orderDO.setOrderId(String.valueOf(idWorker.nextId()));
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
                .map(orderDO -> {
                    // 发起微信支付
                    SubmitOrderParam submitOrderParam = new SubmitOrderParam();
                    // todo 填写参数
                    return submitOrderParam;
                });
        // 调用微信支付
        // todo 编写微信支付
        return wxPayService.submitOrder(param)
                .map(o ->{
                    SubmitOrderVo submitOrderVo = new SubmitOrderVo();
                    return ResultInfo.buildSuccess(submitOrderVo);
                });
    }
}
