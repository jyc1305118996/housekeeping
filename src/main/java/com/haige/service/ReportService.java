package com.haige.service;

import com.haige.common.bean.ResultInfo;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2020/1/13 22:04
 */
public interface ReportService {
    Mono<ResultInfo> quarterEmployeeServiceNumber();

    Mono<ResultInfo> employeeServiceNumber(String startTime, String endTime);

}
