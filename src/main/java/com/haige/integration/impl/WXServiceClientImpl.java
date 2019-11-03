package com.haige.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haige.integration.WXServiceClient;
import com.haige.integration.model.UserinfoResult;
import com.haige.integration.model.WXAccessTokenResult;
import com.haige.integration.param.AccessTokenParam;
import com.haige.integration.param.UserinfoParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 微信服务实现类
 *
 * @author Archie
 * @date 2019/11/1 23:44
 */
@Service
@Slf4j
public class WXServiceClientImpl implements WXServiceClient {

    /**
     * 获取接口权限
     */
    @Value("${wx.accessTokenUrl}")
    public String accessTokenUrl;
    /**
     * 获取用户信息
     */
    @Value("${wx.userInfoUrl}")
    public String userInfoUrl;

    public String grantType = "authorization_code";

    @Autowired
    private RestTemplate restTemplate;

    public Mono<WXAccessTokenResult> getAccessToken(Mono<AccessTokenParam> accessTokenParam) {
        return accessTokenParam
                .map(accessTokenParam1 -> {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("appid", accessTokenParam1.getAppid());
                    param.put("secret", accessTokenParam1.getSecret());
                    param.put("js_code", accessTokenParam1.getCode());
                    param.put("grant_type", grantType);
                    return restTemplate.getForEntity(accessTokenUrl + "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}", String.class, param);
                })
                .map(responseEntity -> {
                    WXAccessTokenResult wxAccessTokenResult = new WXAccessTokenResult();
                    String body = responseEntity.getBody();
                    JSONObject parse = (JSONObject) JSON.parse(body);
                    if (parse.get("errcode") == null) {
                        wxAccessTokenResult.setSessionKey(parse.get("session_key").toString());
                        wxAccessTokenResult.setOpenid(parse.get("openid").toString());
                    } else {
                        throw new RuntimeException("调用微信授权接口出错：" + parse.get("errmsg"));
                    }
                    return wxAccessTokenResult;
                });


    }

    public Mono<UserinfoResult> getUserinfo(Mono<UserinfoParam> userinfoParam) {
//        UserinfoResult userinfo = new UserinfoResult();
//        HashMap<String, String> param = new HashMap<>();
//        param.put("access_token", userinfoParam.getAccessToken());
//        param.put("openid", userinfoParam.getOpenid());
//        param.put("lang", "zh_CN");
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userInfoUrl + "?access_token={access_token}&openid={openid}&lang={lang}", String.class, param);
//        String body = responseEntity.getBody();
//        JSONObject parse = (JSONObject) JSON.parse(body);
//        if (parse.get("errcode") == null) {
//            userinfo.setOpenid(parse.get("openid").toString());
//            userinfo.setNickname(parse.get("nickname").toString());
//            userinfo.setSex(parse.get("sex").toString());
//            userinfo.setProvince(parse.get("province").toString());
//            userinfo.setCity(parse.get("city").toString());
//            userinfo.setCountry(parse.get("country").toString());
//            userinfo.setHeadimgurl(parse.get("headimgurl").toString());
//            Object privilege = parse.get("privilege");
//            // todo 后期处理
//            userinfo.setPrivilege(null);
//            userinfo.setUnionid(parse.get("unionid").toString());
//        } else {
//            throw new RuntimeException("获取微信用户信息失败:" + parse.get("errmsg"));
//        }
//        return userinfo;
        return null;
    }

}
