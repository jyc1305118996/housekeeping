package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.CouponDO;
import com.haige.db.entity.GoodsCouponDO;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.CouponDOMapper;
import com.haige.db.mapper.GoodsCouponDOMapper;
import com.haige.db.mapper.SystemUserMapper;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.integration.WXLoginService;
import com.haige.integration.param.AccessTokenParam;
import com.haige.service.SystemUserService;
import com.haige.service.convert.UserBaseConvertUtils;
import com.haige.service.dto.PhoneLoginDTO;
import com.haige.util.DateUtils;
import com.haige.util.SystemUtils;
import com.haige.web.vo.UserBaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Aaron
 * create at:  2019-10-20  13:38
 * @description:
 */
@Service
@Slf4j
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private UserBaseDOMapper userBaseDOMapper;
    @Autowired
    private GoodsCouponDOMapper goodsCouponDOMapper;

    @Autowired
    private WXLoginService wxLoginService;
    @Autowired
    private CouponDOMapper couponDOMapper;

    /**
     * 手机号登陆
     *
     * @param exchange
     * @param loginVOMono
     * @return
     */
    @Override
    public Mono<ResultInfo<UserBaseVO>> loginByPhoneAndCode(ServerWebExchange exchange, Mono<PhoneLoginDTO> loginVOMono) {
        // 验证session
        Mono<PhoneLoginDTO> phoneLoginVOMono = loginVOMono
                .zipWith(exchange.getSession(), (loginVO, session) -> {
                    String ip = SystemUtils.getIp(exchange);
                    String phone = loginVO.getPhone();
                    String checkCode = loginVO.getCheckCode();
                    String sysCode = session.getAttributes().get(phone) == null ? "" : session.getAttributes().get(phone).toString();
                    log.info("获取到的验证码是,sysCode：{}, checkCode:{}", sysCode, checkCode);
                    //验证码不对
                    if (!checkCode.equals(sysCode)) {
                        throw new RuntimeException("验证码不正确");
                    } else {
                        //验证码正确
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("ip", ip);
                        param.put("user", phone);
                        param.put("type", "2");
                        systemUserMapper.saveLoginLog(param);
                        return loginVO;
                    }
                });
        AtomicReference<String> nickName = new AtomicReference<>();
        AtomicReference<String> avatarUrl = new AtomicReference<>();
        AtomicReference<String>  phone = new AtomicReference<>();
        // 调用微信
        return wxLoginService.getAccessToken(
                phoneLoginVOMono
                        .doOnNext(wxLoginDTO1 -> {
                            nickName.set(wxLoginDTO1.getNickName());
                            avatarUrl.set(wxLoginDTO1.getAvatarUrl());
                            phone.set(wxLoginDTO1.getPhone());
                        })
                        .map(wxLoginDTO1 -> {
                            AccessTokenParam accessTokenParam = new AccessTokenParam();
                            accessTokenParam.setAppid(wxLoginDTO1.getAppid());
                            accessTokenParam.setCode(wxLoginDTO1.getCode());
                            accessTokenParam.setSecret(wxLoginDTO1.getSecret());
                            return accessTokenParam;
                        }))
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
                        userBaseDO.setUbdFixedPhone(phone.get());
                        userBaseDOMapper.insertSelective(userBaseDO);
                        // 创建优惠券
                        GoodsCouponDO goodsCouponDO = goodsCouponDOMapper.selectByPrimaryKey(1);
                        CouponDO couponDO = new CouponDO();
                        couponDO.setUcUserId(userBaseDO.getUbdId());
                        couponDO.setUcUserName(userBaseDO.getUbdName());
                        couponDO.setUcCouponPrice(goodsCouponDO.getGcPrice());
                        couponDO.setUcCouponId(goodsCouponDO.getGcId());
                        couponDO.setUcCouponValidity(DateUtils.convertToString(LocalDateTime.now().plus(30L, ChronoUnit.DAYS)));
                        couponDOMapper.insertSelective(couponDO);
                    }
                    // 每次登陆都刷新昵称和头像
                    // 用户昵称
                    userBaseDO.setUbdPoliceName(nickName.get());
                    // 微信用户识别码
                    userBaseDO.setUbdWechatId(result.getOpenid());
                    userBaseDO.setUbdUpdtTime(new Date());
                    userBaseDO.setUbdFixedPhone(phone.get());
                    String token = UUID.randomUUID().toString();
                    userBaseDO.setUbdToken(token);
                    LocalDateTime expreDate = LocalDateTime.now().plus(30L, ChronoUnit.DAYS);
                    userBaseDO.setUbdTokenExpreDate(DateUtils.convertToString(expreDate));
                    userBaseDOMapper.updateByPrimaryKeySelective(userBaseDO);
                    return userBaseDO;
                })
                .map(userBaseDO -> {
                    ResultInfo<UserBaseVO> resultInfo = new ResultInfo<>(StatusCodeEnum.OK);
                    UserBaseVO userBaseVO = UserBaseConvertUtils.toVO(userBaseDO);
                    resultInfo.setData(userBaseVO);
                    return resultInfo;
                });



    }
}

