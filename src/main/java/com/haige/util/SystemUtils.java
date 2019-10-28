package com.haige.util;

import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 系统工具类
 *
 * @author aaron
 * @date 2019/10/19 0:41
 */
public class SystemUtils {

    /**
     * 获取InetSocketAddress
     * @param exchange
     * @return
     */
    public static InetSocketAddress getInetSocketAddress(ServerWebExchange exchange) {


        return exchange.getRequest().getRemoteAddress();
    }

    /**
     * 获取ip
     * @param exchange
     * @return
     */
    public static String getIp(ServerWebExchange exchange) {

        return getInetSocketAddress(exchange).getAddress().getHostAddress();
    }

}
