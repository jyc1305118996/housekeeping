package com.haige.integration;

import com.haige.integration.param.SendMessageParam;
import com.haige.integration.model.SendMessageResult;

/**
 * 第三方短信
 * @author Archie
 * @date 2019/10/18 0:40
 */
public interface SmsClient {

    /**
     * 短信发送
     * @param sendMessageParam
     */
    SendMessageResult sendMessage(SendMessageParam sendMessageParam);
}
