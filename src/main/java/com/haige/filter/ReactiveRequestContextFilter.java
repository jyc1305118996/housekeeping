package com.haige.filter;

import com.haige.util.ThreadLocalUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author ZiLong
 * @date 2019/8/10 15:50
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveRequestContextFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        // webFlux把request和response封装成ServerWebExchange对象
        ServerHttpRequest request = serverWebExchange.getRequest();
        ThreadLocalUtil.threadLocal.set(Mono.just(request));
        return webFilterChain.filter(serverWebExchange);
    }
}
