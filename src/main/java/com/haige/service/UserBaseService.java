package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.BindDingDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.service.dto.WXLoginDTO;
import com.haige.web.vo.UserBaseVO;
import java.util.List;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/1 21:01
 */
public interface UserBaseService {
    UserBaseDTO save(UserBaseDTO userBaseDTO);

    UserBaseDTO findByToken(String token);

    Mono<ResultInfo<UserBaseVO>> wxLogin(Mono<WXLoginDTO> wxLoginDTO);

    /**
     * 使手机号和用户绑定
     * @param serverWebExchange
     * @param bindDingDTOMono
     * @return
     */
    Mono<ResultInfo> bindingIphone(ServerWebExchange serverWebExchange, Mono<BindDingDTO> bindDingDTOMono);



    /**
     * @description:
     * @param:  * ubdAdmin 用户等级
     * @return: * 用户列表
     * @author: aaron
     * @date: 2019-12-24
     **/
    Mono<ResultInfo<List<UserBaseDTO>>> queryUserList(ServerWebExchange serverWebExchange,Integer ubdAdmin);





}
