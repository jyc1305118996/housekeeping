package com.haige.web.controller;

import com.haige.auth.annotation.Permission;
import com.haige.auth.enums.PermissionType;
import com.haige.common.bean.Result;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.service.SmsService;
import com.haige.service.SystemUserService;
import com.haige.util.FileUtils;
import com.haige.web.convert.SmsConvertUtils;
import com.haige.web.request.SavePersonRequest;
import com.haige.web.vo.SendSmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Archie
 * @date 2019/8/2 22:36
 */
@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {


    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private FileUtils fileUtils;

    @RequestMapping("/log")
    @Permission(PermissionType.ALL)
    public String log() {
        log.info("--------------log-info");
        log.error("-------------log-error");

        return "log success";
    }

    /**
     * 测试参数校验框架
     *
     * @return
     */
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<String> testValid(@RequestBody @Valid Mono<SavePersonRequest> personVOMono) {
        return personVOMono
                .doOnNext(savePersonRequest -> log.info("接受的前端信息是:{}", savePersonRequest))
                .map(savePersonRequest -> "success!!!");
    }

    @RequestMapping("testcode/{phone}/{code}")
    public Mono<ResultInfo<String>> testcode(ServerWebExchange exchange, @PathVariable String phone, @PathVariable String code) {
        log.info(phone);

        return systemUserService.loginByPhoneAndCode(phone, code, exchange);
    }

    @RequestMapping(value = "/baseUploadFile", method = {RequestMethod.POST})
    public Mono<ResultInfo<Object>> baseUploadFile(ServerWebExchange exchange,@RequestPart("file") FilePart filePart) {

        System.out.println("1111111");

        return exchange.getSession().map(webSession -> {


            try {
                ResultInfo<Object> resultInfo = new ResultInfo<Object>();
                resultInfo.setCode(Result.StateType.OK.getCode());
                resultInfo.setData(fileUtils.uploadFile(filePart));
                return resultInfo;
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultInfo<Object>(StatusCode.PROCESSING_EXCEPTION);
            }
        });

    }

    @RequestMapping("testcode1/{phone}/{code}")
    public Mono<ResultInfo<String>> testcode1(ServerWebExchange exchange, @PathVariable String phone, @PathVariable String code) {
        log.info("1x");

        return systemUserService.loginByPhoneAndCode("1", "1", exchange);
    }
}
