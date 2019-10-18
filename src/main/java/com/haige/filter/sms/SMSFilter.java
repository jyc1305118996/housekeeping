package com.haige.filter.sms;


import com.alibaba.fastjson.JSON;
import com.haige.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 短信发送过滤器
 *
 * @author Archie
 * @date 2019/10/16 0:43
 */

@Component
@Slf4j
@Order(1)
public class SMSFilter implements WebFilter {

    @Autowired
    private SmsService smsService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        request.getBody()
                .map(dataBuffer -> {
                    InputStream inputStream = dataBuffer.asInputStream();
                    try {
                        return IOUtils.inputStreamAsString(inputStream, "UTF-8");
                    } catch (IOException e) {
                        log.info("流解析失败{}", e.getCause(), e);
                        throw new RuntimeException("手机号解析失败", e);
                    }
                })
                .map(JSON::parse);


        // 查询短信次数
        // 查询ip次数
        // 存入session
        if (false) {
            log.info("短信可发送");
        } else {

            return response.writeWith(Mono.just(response.bufferFactory().wrap("Exceed the maximum number of days".getBytes(StandardCharsets.UTF_8))));
        }
        return chain.filter(exchange);
    }
}
