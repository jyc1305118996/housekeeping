package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.OrderService;
import com.haige.web.convert.OrderConvertUtils;
import com.haige.web.request.SubmitOrderRequest;
import com.haige.web.vo.SubmitOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Archie
 * @date 2019/11/10 13:45
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 用户下单接口
     * @return
     */
    @PostMapping(value = "/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo<SubmitOrderVo>> submit(ServerWebExchange serverWebExchange, @RequestBody @Valid Mono<SubmitOrderRequest> submitOrderRequest) {
        return orderService.submit(serverWebExchange, submitOrderRequest.map(OrderConvertUtils::toDTO));
    }
}
