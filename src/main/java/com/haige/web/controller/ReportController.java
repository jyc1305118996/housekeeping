package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2020/1/13 21:53
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;


    /**
     * 获取当月报表
     * @return
     */
    @GetMapping(value = "/month", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> month() {
        return reportService.month();
    }
}
