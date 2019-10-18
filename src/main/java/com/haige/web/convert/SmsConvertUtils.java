package com.haige.web.convert;

import com.haige.service.dto.SendSmsDTO;
import com.haige.web.vo.SendSmsRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/10/19 1:33
 */
public class SmsConvertUtils {
    public static SendSmsDTO toDto(SendSmsRequest sendSmsRequest){
        SendSmsDTO sendSmsDTO = new SendSmsDTO();
        BeanUtils.copyProperties(sendSmsRequest, sendSmsDTO);
        return sendSmsDTO;
    }
}
