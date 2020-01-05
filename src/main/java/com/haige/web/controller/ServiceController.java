package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.ServiceService;
import com.haige.service.convert.ServiceConvertUtils;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.convert.ServiceOrderDetailConvertUtils;
import com.haige.web.request.SubmitServiceParam;
import com.haige.web.request.UpdateServiceOrderDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
     *
     * @return
     */
    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> submit(@RequestAttribute("user") UserBaseDTO userBaseDTO, @RequestBody @Valid Mono<SubmitServiceParam> serviceParamMono) {
        return serviceService.submit(userBaseDTO, serviceParamMono.map(ServiceConvertUtils::toDTO));
    }

    @PutMapping(value = "/updateServerDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> updateServerDetail(@RequestBody @Valid Mono<UpdateServiceOrderDetailRequest> request) {
        return serviceService.updateServerDetail(request.map(ServiceOrderDetailConvertUtils::toDTO));
    }


    @GetMapping(value = "/web/queryServiceOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> queryServiceOrderList(@RequestParam("index") int index, @RequestParam("size") int size) {
        return serviceService.queryServiceOrderList(index, size);
    }

    @GetMapping(value = "/onComplete/{serviceId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> onComplete(@PathVariable("serviceId") int serviceId) {
        return serviceService.onComplete(serviceId);
    }
}
