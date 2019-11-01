package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.service.UserBaseService;
import com.haige.service.convert.UserBaseConvertUtils;
import com.haige.service.dto.UserBaseDTO;
import com.haige.service.dto.WXLoginDTO;
import com.haige.util.DateUtils;
import com.haige.web.vo.WXLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @author Archie
 * @date 2019/11/1 21:01
 */
@Service
@Slf4j
public class UserBaseImpl implements UserBaseService {

    @Autowired
    private UserBaseDOMapper userBaseDOMapper;

    public UserBaseDTO save(UserBaseDTO userBaseDTO) {
        UserBaseDO userBaseDO = UserBaseConvertUtils.toDO(userBaseDTO);
        userBaseDOMapper.insertSelective(userBaseDO);
        return userBaseDTO;
    }

    @Override
    public UserBaseDTO findByToken(String token) {
        UserBaseDO userBaseDO = userBaseDOMapper.findByToken(token);
        return UserBaseConvertUtils.toDTO(userBaseDO);
    }

    @Override
    public Mono<ResultInfo<String>> wxLogin(Mono<WXLoginDTO> wxLoginDTO) {
        return wxLoginDTO.map(wxLoginDTO1 -> {
            // todo 微信认证
            return wxLoginDTO1;
        })
                .map(wxLoginDTO1 -> {
                    // 创建一个假用户
                    UserBaseDO userBaseDO = new UserBaseDO();
                    String token = UUID.randomUUID().toString();
                    userBaseDO.setUbdToken(token);
                    LocalDateTime expreDate = LocalDateTime.now().plus(30L, ChronoUnit.DAYS);
                    userBaseDO.setUbdTokenExpreDate(DateUtils.convertToString(expreDate));
                    userBaseDOMapper.insertSelective(userBaseDO);
                    return token;
                }).map(token -> {
                    ResultInfo<String> resultInfo = new ResultInfo<>(StatusCode.OK);
                    resultInfo.setData(token);
                    return resultInfo;
                });
    }
}
