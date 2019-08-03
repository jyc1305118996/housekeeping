package com.haige.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王亚奇
 * @date 2019/8/2 22:36
 */
@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }

    @GetMapping("/log")
    public String log(){
        log.info("--------------log-info");
        log.error("-------------log-error");
        return "log success";
    }

}
