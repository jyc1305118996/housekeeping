package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.SystemUserService;
import com.haige.service.UserBaseService;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.convert.UserBaseConvertUtils;
import com.haige.web.request.BindDingRequest;
import com.haige.web.vo.PhoneLoginVO;
import com.haige.web.vo.UserBaseVO;
import com.haige.web.vo.WXLoginVO;
import java.util.List;
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


    /**
     * 手机号登陆
     * @param exchange
     * @param phone
     * @return
     */
    @PostMapping(value = "/login/phone", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo<UserBaseVO>> testcode(ServerWebExchange exchange, @RequestBody @Valid Mono<PhoneLoginVO> phone) {
        return systemUserService.loginByPhoneAndCode(exchange, phone.map(UserBaseConvertUtils::toDTO));
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
        return userBaseService.wxLogin(wxLoginVO.map(UserBaseConvertUtils::toDTO));
    }

    @PostMapping(value = "/binding/iphone", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> bindingIphone(ServerWebExchange serverWebExchange, @RequestBody @Valid Mono<BindDingRequest> bindDingRequest) {
        return userBaseService.bindingIphone(serverWebExchange, bindDingRequest.map(UserBaseConvertUtils::toDTO));
    }

    @GetMapping(value = "/queryUserList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo<List<UserBaseDTO>>> queryUserList(ServerWebExchange serverWebExchange, @RequestParam(value = "ubdAdmin", required = false) Integer ubdAdmin) {

        if (null == ubdAdmin) {
            ubdAdmin = 0;//状态不传输就默认查所有
        }

        return userBaseService.queryUserList(serverWebExchange,ubdAdmin);
    }
}
