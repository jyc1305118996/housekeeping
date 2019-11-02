package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.SystemUserService;
import com.haige.service.UserBaseService;
import com.haige.web.convert.WXLoginConvertUtils;
import com.haige.web.vo.PhoneLoginVO;
import com.haige.web.vo.UserBaseVO;
import com.haige.web.vo.WXLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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


    @PostMapping(value = "/login/phone", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo<UserBaseVO>> testcode(ServerWebExchange exchange, @RequestBody @Valid Mono<PhoneLoginVO> phone) {
        return systemUserService.loginByPhoneAndCode(exchange, phone.map(WXLoginConvertUtils::toDTO));
    }

    /**
     * 微信登陆
     *
     * @param wxLoginVO
     * @return
     */
    @PostMapping(value = "/login/wx", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo<UserBaseVO>> wxLogin(@RequestBody @Valid Mono<WXLoginVO> wxLoginVO) {
        return userBaseService.wxLogin(wxLoginVO.map(WXLoginConvertUtils::toDTO));
    }
}
