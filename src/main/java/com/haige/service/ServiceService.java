package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.SubmitServiceDTO;
import com.haige.service.dto.UpdateServiceOrderDetailDTO;
import com.haige.service.dto.UserBaseDTO;
import reactor.core.publisher.Mono;

/**
 * 服务接口
 * @author Archie
 * @date 2019/11/20 23:31
 */
public interface ServiceService {
    /**
     * 预约服务
     * @param serviceDTOMono
     * @return
     */
    Mono<ResultInfo> submit(UserBaseDTO userBaseDTO, Mono<SubmitServiceDTO> serviceDTOMono);
    Mono<ResultInfo> updateServerDetail(Mono<UpdateServiceOrderDetailDTO> updateOrderDetailDTOMono);

    Mono<ResultInfo> queryServiceOrderList(int index , int size);

}
