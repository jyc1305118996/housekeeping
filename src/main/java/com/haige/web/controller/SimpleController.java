package com.haige.web.controller;

import com.haige.db.test.TestDao;
import com.haige.web.request.SavePersonRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    private TestDao testDao;

    @GetMapping("/test")
    public String test() {
        //System.out.println("simple");
        return testDao.test().toString();
    }

    @GetMapping("/log")
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

}
