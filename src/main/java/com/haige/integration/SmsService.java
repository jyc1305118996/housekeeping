package com.haige.integration;

import com.haige.integration.param.SendMessageParam;
import com.haige.integration.model.SendMessageResult;

/**
 * @author Archie
 * @date 2019/10/18 0:40
 */
public interface SmsService {

    /**
     * 短信发送
     * @param sendMessageParam
     */
    SendMessageResult sendMessage(SendMessageParam sendMessageParam);
}
