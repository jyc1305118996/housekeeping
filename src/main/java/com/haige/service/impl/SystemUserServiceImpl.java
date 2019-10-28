package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.bean.SystemConstants;
import com.haige.common.enums.StatusCode;
import com.haige.db.mapper.SystemUserMapper;
import com.haige.service.SystemUserService;
import com.haige.util.SystemUtils;
import com.haige.util.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

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

    @Override
    public Mono<ResultInfo<String>> loginByPhoneAndCode(String phone, String code, ServerWebExchange exchange) {
        return exchange.getSession()
                .map(webSession -> {
                    //验证码正确
                    String ip = SystemUtils.getIp(exchange);

                    HashMap<String,String> param = new HashMap<String, String>();
                    param.put("ip",ip);
                    param.put("user",phone);
                    param.put("user","2");
                    systemUserMapper.saveLoginLog(param);
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

                        return new ResultInfo<String>(StatusCode.OK);

                    }


                });


    }
}

