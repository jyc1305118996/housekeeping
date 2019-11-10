package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.SubmitOrderDTO;
import com.haige.web.vo.SubmitOrderVo;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/10 13:56
 */
public interface OrderService {
    /**
     * 统一下单接口
     * @param orderRequestMono
     * @return
     */
    Mono<ResultInfo<SubmitOrderVo>> submit(Mono<SubmitOrderDTO> orderRequestMono);
}
