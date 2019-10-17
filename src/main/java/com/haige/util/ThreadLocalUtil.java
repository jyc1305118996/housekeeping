package com.haige.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/8/10 17:33
 */
public class ThreadLocalUtil {
    public static ThreadLocal<Mono<ServerHttpRequest>> threadLocal = new ThreadLocal();
}
