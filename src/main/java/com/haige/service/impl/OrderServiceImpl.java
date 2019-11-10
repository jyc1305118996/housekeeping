package com.haige.service.impl;

import com.google.common.util.concurrent.AtomicDouble;
import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.GoodsCouponDO;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.mapper.GoodsCouponDOMapper;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.service.OrderService;
import com.haige.service.dto.SubmitOrderDTO;
import com.haige.web.vo.SubmitOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public Mono<ResultInfo<SubmitOrderVo>> submit(Mono<SubmitOrderDTO> orderRequestMono) {
        orderRequestMono
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
                    return goodsPrice;
                });

        // 调用微信支付
        // 返回
        return null;
    }
}
