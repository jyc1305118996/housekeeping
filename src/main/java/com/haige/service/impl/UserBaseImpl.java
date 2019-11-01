package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.integration.WXServiceClient;
import com.haige.integration.model.UserinfoResult;
import com.haige.integration.model.WXAccessTokenResult;
import com.haige.integration.param.AccessTokenParam;
import com.haige.integration.param.UserinfoParam;
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

    @Autowired
    private WXServiceClient wxServiceClient;

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
        return wxLoginDTO
                .map(wxLoginDTO1 -> {
                    AccessTokenParam accessTokenParam = new AccessTokenParam();
                    accessTokenParam.setAppid(wxLoginDTO1.getAppid());
                    accessTokenParam.setCode(wxLoginDTO1.getCode());
                    // 获取接口权限
                    WXAccessTokenResult result = wxServiceClient.getAccessToken(accessTokenParam);
                    UserinfoParam userinfoParam = new UserinfoParam();
                    userinfoParam.setOpenid(result.getOpenid());
                    userinfoParam.setAccessToken(result.getAccessToken());
                    // 获取用户信息
                    UserinfoResult userinfo = wxServiceClient.getUserinfo(userinfoParam);
                    return userinfo;
                })
                .map(userinfo -> {
                    UserBaseDO userBaseDO = userBaseDOMapper.findByUnionid(userinfo.getUnionid());
                    if (userBaseDO == null) {
                        // 新用户
                        userBaseDO = new UserBaseDO();
                        // 用户昵称
                        userBaseDO.setUbdPoliceName(userinfo.getNickname());
                        // 微信用户识别码
                        userBaseDO.setUbdWechatId(userinfo.getUnionid());
                        userBaseDOMapper.insertSelective(userBaseDO);
                    }
                    String token = UUID.randomUUID().toString();
                    userBaseDO.setUbdToken(token);
                    LocalDateTime expreDate = LocalDateTime.now().plus(30L, ChronoUnit.DAYS);
                    userBaseDO.setUbdTokenExpreDate(DateUtils.convertToString(expreDate));
                    userBaseDOMapper.updateByPrimaryKey(userBaseDO);
                    return token;
                }).map(token -> {
                    ResultInfo<String> resultInfo = new ResultInfo<>(StatusCode.OK);
                    resultInfo.setData(token);
                    return resultInfo;
                });
    }
}
