package com.haige.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.haige.integration.SmsClient;
import com.haige.integration.param.SendMessageParam;
import com.haige.integration.model.SendMessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Archie
 * @date 2019/10/18 0:44
 */
@Service
@Slf4j
public class SmsClientImpl implements SmsClient {

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessSecret}")
    private String accessSecret;

    @Value("${aliyun.sms.signName}")
    private String signName;


    @Override
    public SendMessageResult sendMessage(SendMessageParam sendMessageParam) {
        log.debug("iphone:{},message:{}", sendMessageParam.getIphone(), sendMessageParam.getSmsTemplate().getParam());
        SendMessageResult sendMessageResult = new SendMessageResult();
        sendMessageResult.setSmsStatus("success");

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", sendMessageParam.getIphone());
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", sendMessageParam.getSmsTemplate().getTemplateCode());
        String paramJson = JSON.toJSONString(sendMessageParam.getSmsTemplate().getParam());
        request.putQueryParameter("TemplateParam", paramJson);
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getHttpStatus() == 200){
                JSONObject json = (JSONObject)JSON.parse(response.getData());
                if (!"OK".equals(json.get("Message"))){
                    sendMessageResult.setSmsStatus("faild");
                    log.error("短信发送失败：{}",json.get("Message") );
                    sendMessageResult.setBadReason(json.get("Message").toString());
                }
            }
        } catch (ClientException e) {
            throw new RuntimeException("短信发送失败", e);
        }
        return sendMessageResult;
    }
}
