package com.haige.auth;

import com.haige.common.enums.StatusCode;
import com.haige.service.UserBaseService;
import com.haige.service.dto.UserBaseDTO;
import com.haige.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Archie
 * @date 2019/10/20 18:26
 */
@Order(10)
@Component
@Slf4j
public class AuthFilter implements WebFilter {


    @Autowired
    private UserBaseService userBaseService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 非登陆和短信全过滤
        boolean isLogin = !request.getPath().value().contains("/login");
        boolean isSms = !request.getPath().value().contains("/sms/send");
        if (isLogin && isSms){
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            List<String> auth = request.getHeaders().get("Authorization");
            if (auth == null || auth.isEmpty()){
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.NOT_LOGIN.getCode() + "\", \"message\":\"" + StatusCode.NOT_LOGIN.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
            }
            try{
                UserBaseDTO userBaseDTO= userBaseService.findByToken(auth.get(0));
                // 未登录
                if (userBaseDTO == null){
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.NOT_LOGIN.getCode() + "\", \"message\":\"" + StatusCode.NOT_LOGIN.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
                }
                String expreDate = userBaseDTO.getUbdTokenExpreDate();
                LocalDateTime dateTime = DateUtils.convertToDateTime(expreDate);
                // 过期
                if (dateTime.isBefore(LocalDateTime.now())){
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.BEOVERDUE.getCode() + "\", \"message\":\"" + StatusCode.BEOVERDUE.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
                }
            }catch (Exception e){
                // 未认证非法请求
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.NOT_LOGIN.getCode() + "\", \"message\":\"" + StatusCode.NOT_LOGIN.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
            }
        }
        // 认证通过
        return chain.filter(exchange);
    }
}
