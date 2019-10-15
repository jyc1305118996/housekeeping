package com.haige.filter.sms;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
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
