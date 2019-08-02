package com.haige.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王亚奇
 * @date 2019/8/2 22:36
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
