package com.haige.integration;

import com.haige.integration.dto.SendMessageDto;
import com.haige.integration.dto.SendMessageResponse;

/**
 * @author Archie
 * @date 2019/10/18 0:40
 */
public interface SmsServiceClient {

    /**
     * 短信发送
     * @param sendMessageDto
     */
    SendMessageResponse sendMessage(SendMessageDto sendMessageDto);
}
