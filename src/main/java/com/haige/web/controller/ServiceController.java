package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.ServiceService;
import com.haige.service.convert.ServiceConvertUtils;
import com.haige.web.request.SubmitServiceParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * 服务接口
 *
 * @author Archie
 * @date 2019/11/20 23:24
 */
@RestController
@RequestMapping("/service")
public class ServiceController {


    @Autowired
    private ServiceService serviceService;

    /**
     * 用户预约服务接口
     * @return
     */
    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> submit(@RequestBody @Valid Mono<SubmitServiceParam> serviceParamMono) {
        return serviceService.submit(serviceParamMono.map(ServiceConvertUtils::toDTO));
    }
}
