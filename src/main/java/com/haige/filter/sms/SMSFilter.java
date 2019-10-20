package com.haige.filter.sms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.haige.service.SmsService;
import com.haige.util.DateUtils;
import com.haige.web.vo.SendSmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 短信发送过滤器
 *
 * @author Archie
 * @date 2019/10/16 0:43
 */

@Component
@Slf4j
@Order(11)
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
                    .map(data -> {
                        Object json = JSON.parse(data);
                        JSONObject jsonObject = (JSONObject) json;
                        SendSmsRequest smsRequest = new SendSmsRequest();
                        Object iphone= jsonObject.get("iphone");
                        Object type = jsonObject.get("type");
                        Preconditions.checkArgument(!StringUtils.isEmpty(iphone), "手机号不能为空");
                        Preconditions.checkArgument(!StringUtils.isEmpty(type), "业务类型不能为空");
                        smsRequest.setIphone(iphone.toString());
                        smsRequest.setType(type.toString());
                        smsRequest.setIp(request.getRemoteAddress().getAddress().toString());
                        exchange.getAttributes().put("isError", false);
                        exchange.getAttributes().put("sendSmsRequest", Mono.just(smsRequest));
                        smsService.findList(null, smsRequest.getIphone())
                                .subscribe(shortMsgDO -> {
                                    // 上次发送短信时间加上60秒是否在当前时间之前
                                    boolean isAfter = DateUtils.convertToDateTime(shortMsgDO.getSmiSenderTime()).plus(60, ChronoUnit.SECONDS).isAfter(LocalDateTime.now());
                                    if (isAfter){
                                        exchange.getAttributes().put("isError", true);
                                        exchange.getAttributes().put("message", "当前手机号超频请求短信");
                                    }
                                });
                        if ((boolean) exchange.getAttributes().get("isError")) {
                            return smsRequest;
                        }
                        smsService.findList(smsRequest.getIp(), null)
                                .subscribe(shortMsgDO -> {
                                    // 上次发送短信时间加上60秒是否在当前时间之前
                                    boolean isAfter = DateUtils.convertToDateTime(shortMsgDO.getSmiSenderTime()).plus(60, ChronoUnit.SECONDS).isAfter(LocalDateTime.now());
                                    if (isAfter){
                                        exchange.getAttributes().put("isError", true);
                                        exchange.getAttributes().put("message", "当前ip超频请求短信");
                                    }
                                });
                        if ((boolean) exchange.getAttributes().get("isError")) {
                            return smsRequest;
                        }
                        smsService.findByIp(request.getRemoteAddress().getAddress().toString(), DateUtils.nowOfDate())
                                .count()
                                .subscribe(count -> {
                                    if (count > IP_MAX) {
                                        log.info("ip:{}超出最大限制", request.getRemoteAddress().getAddress().toString());
                                        exchange.getAttributes().put("isError", true);
                                        exchange.getAttributes().put("message", "超出当日ip最大限制");
                                    }
                                });
                        if ((boolean) exchange.getAttributes().get("isError")) {
                            return smsRequest;
                        }
                        smsService.findByIphone(smsRequest.getIphone(), DateUtils.nowOfDate())
                                .count()
                                .subscribe(count -> {
                                    if (count > IPHONE_MAX) {
                                        log.info("iphone:{}超出最大限制", smsRequest.getIphone());
                                        exchange.getAttributes().put("isError", true);
                                        exchange.getAttributes().put("message", "超出当日手机号最大限制");
                                    }
                                });
                        return smsRequest;
                    })
                    .subscribe(
                            smsRequest -> {
                                log.info("手机号:{}, ip:{}, message:{}", smsRequest.getIphone(), smsRequest.getIp(), exchange.getAttributes().get("message"));
                            },
                            ex -> {
                                log.info("异常信息:{}", ex.getMessage(), ex);
                                exchange.getAttributes().put("isError", true);
                                exchange.getAttributes().put("message", ex.getMessage());
                            });
            if ((boolean) exchange.getAttributes().get("isError")) {
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"400\", \"message\":\" " + exchange.getAttributes().get("message") + " \", \"data\":\"\", \"count\":\"\"}").getBytes())));
            } else {
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
