package com.haige.integration.impl;

import com.haige.integration.SmsServiceClient;
import com.haige.integration.dto.SendMessageDto;
import com.haige.integration.dto.SendMessageResponse;
import org.springframework.stereotype.Service;

/**
 * @author Archie
 * @date 2019/10/18 0:44
 */
@Service
public class SmsServiceClientImpl implements SmsServiceClient {
    @Override
    public SendMessageResponse sendMessage(SendMessageDto sendMessageDto) {
        // todo
        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        sendMessageResponse.setMessage(sendMessageDto.getMessage());
        sendMessageResponse.setSmsStatus("success");
        return sendMessageResponse;
    }
}
