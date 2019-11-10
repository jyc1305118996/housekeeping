package com.haige.integration.impl;

import com.haige.integration.WXPayService;
import com.haige.integration.param.SubmitOrderParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Archie
 * @date 2019/11/10 14:18
 */
@Service
@Slf4j
public class WXPayServiceImpl implements WXPayService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void submitOrder(SubmitOrderParam submitOrderParam) {

    }
}
