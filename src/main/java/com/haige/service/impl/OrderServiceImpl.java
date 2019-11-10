package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.service.OrderService;
import com.haige.service.dto.SubmitOrderDTO;
import com.haige.web.vo.SubmitOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/10 13:58
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    public Mono<ResultInfo<SubmitOrderVo>> submit(Mono<SubmitOrderDTO> orderRequestMono) {
//        orderRequestMono
//                .map(submitOrderDTO -> {
//                     查询套餐金额,查询优惠券金额,计算出总金额并返回
//                })

        // 调用微信支付
        // 返回
        return null;
    }
}
