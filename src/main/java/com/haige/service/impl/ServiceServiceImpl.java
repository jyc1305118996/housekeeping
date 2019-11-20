package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.db.mapper.ServeDetailDOMapper;
import com.haige.service.ServiceService;
import com.haige.service.dto.SubmitServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/20 23:31
 */
@Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServeDetailDOMapper serveDetailDOMapper;
    @Override
    public Mono<ResultInfo> submit(Mono<SubmitServiceDTO> serviceDTOMono) {
        // todo 预约服务， 预约表需要添加电话，联系人，地址(自动拉取)
        return null;
    }
}
