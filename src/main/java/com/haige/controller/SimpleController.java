package com.haige.controller;

import com.haige.db.test.TestDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SimpleController(){

        System.out.println("wojia");
    }

    @Autowired
    private TestDao testDao;

    @GetMapping("/test")
    public String test(){
        //System.out.println("simple");
        return testDao.test().toString();
    }

    @GetMapping("/log")
    public String log(){
       // log.info("--------------log-info");
       // log.error("-------------log-error");
        return "log success";
    }

    public static void main(String[] args) {
        Object o=null;
        Object o1=o;
        o=new Object();
        System.out.println(o1==o);
    }
}
