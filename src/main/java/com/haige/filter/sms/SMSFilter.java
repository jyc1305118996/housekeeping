package com.haige.filter.sms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haige.service.SmsService;
import com.haige.util.DateUtils;
import com.haige.web.vo.SendSmsRequest;
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

    /**
     * 一个手机号当日最大10条
     */
    private final int IPHONE_MAX = 10;
    /**
     * 一个ip当日最大100条
     */
    private final int IP_MAX = 100;

    @Autowired
    private SmsService smsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        if (request.getPath().value().contains("/sms/send")) {
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
                    .subscribe(
                            data -> {
                                Object json = JSON.parse(data);
                                JSONObject jsonObject = (JSONObject) json;
                                SendSmsRequest smsRequest = new SendSmsRequest();
                                smsRequest.setIphone(jsonObject.get("iphone").toString());
                                smsRequest.setType(jsonObject.get("type").toString());
                                smsRequest.setIp(request.getRemoteAddress().getAddress().toString());
                                smsService.findByIp(request.getRemoteAddress().getAddress().toString(), DateUtils.nowOfDate())
                                        .count()
                                        .subscribe(count -> {
                                            if (count > IP_MAX) {
                                                log.info("ip:{}超出最大限制", request.getRemoteAddress().getAddress().toString());
                                                exchange.getAttributes().put("isError", true);
                                                throw new RuntimeException("超出ip最大限制");
                                            }
                                        });
                                smsService.findByIphone(smsRequest.getIphone(), DateUtils.nowOfDate())
                                        .count()
                                        .subscribe(count -> {
                                            if (count > IPHONE_MAX) {
                                                log.info("iphone:{}超出最大限制", smsRequest.getIphone());
                                                exchange.getAttributes().put("isError", true);
                                                throw new RuntimeException("超出iphone最大限制");
                                            }
                                        });
                                log.info("正常可发送");
                                exchange.getAttributes().put("isError", false);
                                exchange.getAttributes().put("sendSmsRequest", smsRequest);
                            },
                            ex -> {
                                log.info("异常信息:{}", ex.getMessage(), ex);
                                exchange.getAttributes().put("isError", true);
                                exchange.getAttributes().put("message", ex.getMessage());
                            });
            if ((boolean)exchange.getAttributes().get("isError")) {
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"400\", \"message\":\" " + exchange.getAttributes().get("message") + " \", \"data\":\"\", \"count\":\"\"}").getBytes())));
            } else {
                log.info("短信可发送");
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
