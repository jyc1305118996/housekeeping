package com.haige.integration;

import com.haige.integration.param.SubmitOrderParam;

/**
 * @author Archie
 * @date 2019/11/10 14:15
 */
public interface WXPayService {

    void submitOrder(SubmitOrderParam submitOrderParam);
}
