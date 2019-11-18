package com.haige.integration;

import com.haige.integration.model.SubmitOrderResult;
import com.haige.integration.param.SubmitOrderParam;
import reactor.core.publisher.Mono;

/**
 * 微信支付
 * @author Archie
 * @date 2019/11/10 14:15
 */
public interface WXPayService {

    /**
     * 统一下单接口
     * @param submitOrderParam
     */
    Mono<SubmitOrderResult> submitOrder(Mono<String> submitOrderParam);
}
