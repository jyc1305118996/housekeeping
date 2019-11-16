package com.haige.integration.impl;

import com.haige.integration.WXPayService;
import com.haige.integration.convert.WXPayConvertUtils;
import com.haige.integration.enums.WXUrlEnums;
import com.haige.integration.model.SubmitOrderResult;
import com.haige.integration.param.SubmitOrderParam;
import com.haige.util.wxUtil.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Map;

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
    public Mono<SubmitOrderResult> submitOrder(Mono<String> submitOrderParam) {
        return submitOrderParam.map(param -> {
            SubmitOrderResult submitOrderResult = new SubmitOrderResult();
            log.debug("微信支付发起的数据请求是:{}", param);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> formEntity = new HttpEntity<>(param, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(WXUrlEnums.SUBMIT_ORDER_URL, formEntity, String.class);
            String xmlResponse = stringResponseEntity.getBody();
            log.debug("微信支付发起的结果是:{}", xmlResponse);
            try {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlResponse);
                if (map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")){
                    submitOrderResult.setPrepayId(map.get("prepay_id"));
                }
            } catch (Exception e) {
                log.error("微信支付结果解析失败");
                throw new RuntimeException("微信支付结果解析失败", e);
            }
            return submitOrderResult;
        });
    }
}

