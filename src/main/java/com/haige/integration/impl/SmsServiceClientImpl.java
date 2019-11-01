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
import com.haige.integration.SmsServiceClient;
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
public class SmsServiceClientImpl implements SmsServiceClient {

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessSecret}")
    private String accessSecret;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @Value("${aliyun.sms.signName}")
    private String signName;


    @Override
    public SendMessageResult sendMessage(SendMessageParam sendMessageParam) {
        SendMessageResult sendMessageResult = new SendMessageResult();
        sendMessageResult.setMessage(sendMessageParam.getMessage());
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
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + sendMessageParam.getMessage()+ "\"}");
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
