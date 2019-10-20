package com.haige.auth;

import com.haige.common.JWTUtil;
import com.haige.common.enums.StatusCode;
import io.jsonwebtoken.Claims;
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

import java.util.Date;
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
    private JWTUtil jwtUtil;


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
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.BEOVERDUE.getCode() + "\", \"message\":\"" + StatusCode.BEOVERDUE.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
            }
            try{
                Claims claims = jwtUtil.parseJwt(auth.get(0));
                // todo 等待userService完善,判断是否本应用
                String id = claims.getId();
                String subject = claims.getSubject();
                Date expiration = claims.getExpiration();
                if (expiration.getTime() < System.currentTimeMillis()){
                    // token过期
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.BEOVERDUE.getCode() + "\", \"message\":\"" + StatusCode.BEOVERDUE.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
                }
            }catch (Exception e){
                return response.writeWith(Mono.just(response.bufferFactory().wrap(("{\"code\":\"" + StatusCode.BEOVERDUE.getCode() + "\", \"message\":\"" + StatusCode.BEOVERDUE.getValue() + "\", \"data\":\"\", \"count\":\"\"}").getBytes())));
            }
        }
        // 认证通过
        return chain.filter(exchange);
    }
}
