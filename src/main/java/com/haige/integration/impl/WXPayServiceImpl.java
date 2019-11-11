package com.haige.integration.impl;

import com.haige.integration.WXPayService;
import com.haige.integration.convert.WXPayConvertUtils;
import com.haige.integration.enums.WXUrlEnums;
import com.haige.integration.param.SubmitOrderParam;
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
    public Mono<Object> submitOrder(Mono<SubmitOrderParam> submitOrderParam) {
        return submitOrderParam.map(submitOrderParam1 -> {
//            String xml = WXPayConvertUtils.convert(submitOrderParam1);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_XML);
//            HttpEntity<String> formEntity = new HttpEntity<String>(xml, headers);
//            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(WXUrlEnums.SUBMIT_ORDER_URL, formEntity, String.class);
//            System.out.println(stringResponseEntity.getBody());
            System.out.println("aa");
            return submitOrderParam;
        });
    }
}

