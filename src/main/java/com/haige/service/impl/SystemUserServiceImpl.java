package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.bean.SystemConstants;
import com.haige.common.enums.StatusCode;
import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.SystemUserMapper;
import com.haige.db.mapper.UserBaseDOMapper;
import com.haige.service.SystemUserService;
import com.haige.service.UserBaseService;
import com.haige.util.DateUtils;
import com.haige.util.SystemUtils;
import com.haige.util.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

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

    @Override
    public Mono<ResultInfo<String>> loginByPhoneAndCode(String phone, String code, ServerWebExchange exchange) {
        return exchange.getSession()
                .map(webSession -> {
                    //验证码正确
                    String ip = SystemUtils.getIp(exchange);


                    //return new ResultInfo<String>(StatusCode.OK);

                    //参数唯为空
                    if ("".equals(phone) || "".equals(code)) {

                        return new ResultInfo<String>(StatusCode.UNPROCESSABLE_ENTITY);
                    }

                    String sysCode = webSession.getAttributes().get(phone) == null ? "" : webSession.getAttributes().get(phone).toString();
                    //验证码不对
                    if (!code.equals(sysCode)) {

                        return new ResultInfo<String>(StatusCode.UNAUTHORIZED);
                    } else {
//                        //验证码正确
//                        String ip = SystemUtils.getIp(exchange);
//
//                        HashMap<String,String> param = new HashMap<String, String>();
//                        param.put("ip",ip);
//                        param.put("user",phone);
//                        param.put("user","2");

                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("ip", ip);
                        param.put("user", phone);
                        param.put("type", "2");
                        systemUserMapper.saveLoginLog(param);
                        // todo 创建一个假用户
                        UserBaseDO userBaseDO = new UserBaseDO();
                        String token = UUID.randomUUID().toString();
                        userBaseDO.setUbdToken(token);
                        LocalDateTime expreDate = LocalDateTime.now().plus(7L, ChronoUnit.DAYS);
                        userBaseDO.setUbdTokenExpreDate(DateUtils.convertToString(expreDate));
                        userBaseDOMapper.insertSelective(userBaseDO);
                        ResultInfo<String> resultInfo = new ResultInfo<>(StatusCode.OK);
                        resultInfo.setData(token);
                        return resultInfo;

                    }
                });


    }
}

