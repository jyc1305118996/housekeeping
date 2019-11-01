package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.SystemUserService;
import com.haige.service.UserBaseService;
import com.haige.web.convert.WXLoginConvertUtils;
import com.haige.web.vo.WXLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Archie
 * @date 2019/11/1 21:44
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserBaseController {

    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private SystemUserService systemUserService;


    @GetMapping("/login/phone/{phone}/{code}")
    public Mono<ResultInfo<String>> testcode(ServerWebExchange exchange, @PathVariable String phone , @PathVariable String code) {
        log.info(phone);
        return systemUserService.loginByPhoneAndCode(phone, code, exchange);
    }

    @PostMapping("/login/wx")
    public Mono<ResultInfo<String>> wxLogin(@RequestBody @Valid Mono<WXLoginVO> wxLoginVO){
        return userBaseService.wxLogin(wxLoginVO.map(WXLoginConvertUtils::toDTO));
    }
}
