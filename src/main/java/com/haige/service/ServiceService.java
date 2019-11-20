package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.SubmitServiceDTO;
import reactor.core.publisher.Mono;

/**
 * 服务接口
 * @author Archie
 * @date 2019/11/20 23:31
 */
public interface ServiceService {
    Mono<ResultInfo> submit(Mono<SubmitServiceDTO> serviceDTOMono);
}
