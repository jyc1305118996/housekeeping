package com.haige.filter;

import com.haige.util.ThreadLocalUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * @author Archie
 * @date 2019/8/10 15:54
 */
public class ReactiveRequestContextHolder {
    public static Mono<ServerHttpRequest> getRequest() {
        // TODO: 2019/8/10  待寻找新的解决方案
        Mono<ServerHttpRequest> serverHttpRequestMono = ThreadLocalUtil.threadLocal.get();
        // 防止线程长时间存活，堆溢出
        ThreadLocalUtil.threadLocal.remove();
        return serverHttpRequestMono;
    }
}
