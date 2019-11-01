package com.haige.integration.impl;

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

    @Value("${wx.secret}")
    public String secret;

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

    public WXAccessTokenResult getAccessToken(AccessTokenParam accessTokenParam) {
        WXAccessTokenResult wxAccessTokenResult = new WXAccessTokenResult();
        HashMap<String, String> param = new HashMap<>();
        param.put("appid", accessTokenParam.getAppid());
        param.put("secret", secret);
        param.put("code", accessTokenParam.getCode());
        param.put("grant_type", grantType);
        ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(accessTokenUrl, HashMap.class, param);
        HashMap<String, String> body = responseEntity.getBody();
        if (body.get("errcode") == null){
            wxAccessTokenResult.setAccessToken(body.get("access_token"));
            wxAccessTokenResult.setOpenid(body.get("openid"));
        }else {
            throw new RuntimeException("调用微信授权接口出错："+ body.get("errmsg"));
        }
        return wxAccessTokenResult;
    }

    public UserinfoResult getUserinfo(UserinfoParam userinfoParam){
        UserinfoResult userinfo = new UserinfoResult();
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", userinfoParam.getAccessToken());
        param.put("openid", userinfoParam.getOpenid());
        param.put("lang", "zh_CN");
        ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(userInfoUrl, HashMap.class, param);
        HashMap body = responseEntity.getBody();
        if (body.get("errcode") == null){
            userinfo.setOpenid(body.get("openid").toString());
            userinfo.setNickname(body.get("nickname").toString());
            userinfo.setSex(body.get("sex").toString());
            userinfo.setProvince(body.get("province").toString());
            userinfo.setCity(body.get("city").toString());
            userinfo.setCountry(body.get("country").toString());
            userinfo.setHeadimgurl(body.get("headimgurl").toString());
            Object privilege = body.get("privilege");
            // todo 后期处理
            userinfo.setPrivilege(null);
            userinfo.setUnionid(body.get("unionid").toString());
        }else {
            throw new RuntimeException("获取微信用户信息失败:" + body.get("errmsg"));
        }
        return userinfo;
    }

}
