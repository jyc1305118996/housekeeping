package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.integration.WXLoginService;
import com.haige.integration.param.AccessTokenParam;
import com.haige.service.UserBaseService;
import com.haige.service.convert.UserBaseConvertUtils;
import com.haige.service.dto.BindDingDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.service.dto.WXLoginDTO;
import com.haige.util.DateUtils;
import com.haige.web.vo.OrderDetailsVO;
import com.haige.web.vo.UserBaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
    private WXLoginService wxLoginService;

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
    public Mono<ResultInfo<UserBaseVO>> wxLogin(Mono<WXLoginDTO> wxLoginDTO) {
        AtomicReference<String> nickName = new AtomicReference<>();
        AtomicReference<String> avatarUrl = new AtomicReference<>();
        AtomicReference<String> gender = new AtomicReference<>();
        return wxLoginService
                .getAccessToken(
                        // 参数转换
                        wxLoginDTO
                                .doOnNext(wxLoginDTO1 -> {
                                    nickName.set(wxLoginDTO1.getNickName());
                                    avatarUrl.set(wxLoginDTO1.getAvatarUrl());
                                    gender.set(wxLoginDTO1.getGender());
                                })
                                .map(wxLoginDTO1 -> {
                                    AccessTokenParam accessTokenParam = new AccessTokenParam();
                                    accessTokenParam.setAppid(wxLoginDTO1.getAppid());
                                    accessTokenParam.setCode(wxLoginDTO1.getCode());
                                    accessTokenParam.setSecret(wxLoginDTO1.getSecret());
                                    return accessTokenParam;
                                })
                )
                .map(result -> {
                    // 结果处理
                    UserBaseDO userBaseDO = userBaseDOMapper.findByUnionid(result.getOpenid());
                    if (userBaseDO == null) {
                        // 新用户
                        userBaseDO = new UserBaseDO();
                        userBaseDO.setUbdCrteTime(new Date());
                        // 头像
                        userBaseDO.setUbdHeadPortrait(avatarUrl.get());
                        userBaseDO.setUbdIsNew("1");
                        userBaseDOMapper.insertSelective(userBaseDO);
                    }
                    // 每次登陆都刷新昵称和头像
                    // 用户昵称
                    userBaseDO.setUbdPoliceName(nickName.get());
                    // 微信用户识别码
                    userBaseDO.setUbdWechatId(result.getOpenid());
                    String token = UUID.randomUUID().toString();
                    userBaseDO.setUbdToken(token);
                    userBaseDO.setUbdUpdtTime(new Date());
                    LocalDateTime expreDate = LocalDateTime.now().plus(30L, ChronoUnit.DAYS);
                    userBaseDO.setUbdTokenExpreDate(DateUtils.convertToString(expreDate));
                    userBaseDOMapper.updateByPrimaryKeySelective(userBaseDO);
                    return userBaseDO;
                }).map(userBaseDO -> {
                    UserBaseVO userBaseVO = UserBaseConvertUtils.toVO(userBaseDO);
                    ResultInfo<UserBaseVO> resultInfo = new ResultInfo<>(StatusCodeEnum.OK);
                    resultInfo.setData(userBaseVO);
                    return resultInfo;
                });
    }

    @Override
    public Mono<ResultInfo> bindingIphone(ServerWebExchange serverWebExchange, Mono<BindDingDTO> bindDingDTOMono) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        return bindDingDTOMono
                .zipWith(serverWebExchange.getSession(), (param, session) -> {
                    String checkCode = param.getCheckCode();
                    String temCode = session.getAttribute(param.getIphone());
                    log.debug("session验证码:{}, 传入验证码:{}",temCode, checkCode);
                    if (!checkCode.equals(temCode)) {
                        throw new RuntimeException("验证码不正确，绑定失败");
                    }
                    return param;
                })
                .map(param -> {
                    // 关联用户
                    List<String> tokens = request.getHeaders().get("Authorization");
                    UserBaseDO userBaseDO = userBaseDOMapper.findByToken(tokens.get(0));
                    userBaseDO.setUbdFixedPhone(param.getIphone());
                    userBaseDOMapper.updateByPrimaryKey(userBaseDO);
                    return ResultInfo.buildFailed("OK");
                });
    }

  /**
   * @description:
   * @param:  * ubdAdmin 用户等级
   * @return: * 用户列表
   * @author: aaron
   * @date: 2019-12-24
   **/
  @Override
  public Mono<ResultInfo<List<UserBaseDTO>>> queryUserList(ServerWebExchange serverWebExchange,
      Integer ubdAdmin) {

    List<UserBaseDTO> userList = userBaseDOMapper.findUserListBYUbdAdmin(ubdAdmin);
    ResultInfo<List<UserBaseDTO>> result = new ResultInfo<List<UserBaseDTO>>();
    result.setData(userList);
    result.setCount(String.valueOf(userList.size()));
    result.setCode(StatusCodeEnum.OK.getCode());
    result.setMessage(StatusCodeEnum.OK.getValue());
    return Mono.justOrEmpty(result);  }
}
