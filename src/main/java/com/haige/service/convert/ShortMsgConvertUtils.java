package com.haige.service.convert;

import com.haige.db.entity.ShortMsgDO;
import com.haige.integration.param.SendMessageParam;
import com.haige.service.dto.SendSmsDTO;
import com.haige.util.DateUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;

/**
 * @author Archie
 * @date 2019/10/19 11:54
 */
public class ShortMsgConvertUtils {
    public static ShortMsgDO toDo(SendSmsDTO sendSmsDTO){
        ShortMsgDO shortMsgDO = new ShortMsgDO();
        BeanUtils.copyProperties(sendSmsDTO, shortMsgDO);
        shortMsgDO.setSmiSenderTime(DateUtils.nowOfDateTime());
        return shortMsgDO;
    }

    /**
     * 验证码模板
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getSecurityCodeTemplate(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_176523402");
        smsTemplate.setParam(param);
        return smsTemplate;
    }

    /**
     * 获取预约通知模板
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getAppointmentNotifyTemplate(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_176943875");
        smsTemplate.setParam(param);
        return smsTemplate;
    }

    /**
     * 预约受理模板
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getAppointmentAcceptTemplate(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_176913804");
        smsTemplate.setParam(param);
        return smsTemplate;
    }
}
