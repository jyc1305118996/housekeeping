package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.UserBaseDTO;
import com.haige.service.dto.WXLoginDTO;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/1 21:01
 */
public interface UserBaseService {
    UserBaseDTO save(UserBaseDTO userBaseDTO);

    UserBaseDTO findByToken(String token);

    Mono<ResultInfo<String>> wxLogin(Mono<WXLoginDTO> wxLoginDTO);
}
