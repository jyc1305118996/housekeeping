package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.SmsService;
import com.haige.web.convert.SmsConvertUtils;
import com.haige.web.vo.SendSmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 短信接口类
 *
 * @author Archie
 * @date 2019/10/19 0:27
 */
@RestController
@Slf4j
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public Mono<ResultInfo<String>> sendSms(ServerWebExchange exchange, @RequestAttribute("sendSmsRequest") Mono<SendSmsRequest> sendSmsRequest) {
        return smsService.sendSms(sendSmsRequest.map(SmsConvertUtils::toDto), exchange.getSession());
    }
}
