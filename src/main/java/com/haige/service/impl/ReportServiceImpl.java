package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.EmployeeServiceNumberDO;
import com.haige.db.entity.ReportMonthDO;
import com.haige.db.mapperExtend.ReportMapper;
import com.haige.service.ReportService;
import com.haige.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Archie
 * @date 2020/1/13 22:05
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;


    @Override
    public Mono<ResultInfo> quarterEmployeeServiceNumber() {
        // 获取当月1号
        LocalDate now = LocalDate.now();
        String month = DateUtils.convertToString(LocalDate.of(now.getYear(), now.getMonth(), 1));
        List<ReportMonthDO> reportMonthDOList = reportMapper.month(month);
        return null;
    }

    @Override

    public Mono<ResultInfo> employeeServiceNumber(String startTime, String endTime) {
        return Mono.fromSupplier(() -> reportMapper.employeeServiceNumber(startTime, endTime))
                .map(ResultInfo::buildSuccess);
    }
}
