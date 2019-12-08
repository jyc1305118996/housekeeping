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
     * 您有新的订单预约服务，手机号:${phone}，地址:${address}，请及时处理。
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
     * 您预约的服务已为您安排保洁，保洁人员电话${phone},将会于${date}联系您
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

    /**
     * 亲爱的会员，您在安居在家购买的套餐:${goods}付款成功。
     * 订单号为：${orderid},我们会尽快让商家与您联系，记得关注我们的商城喔~感谢您的支持！
     * 支付ok，通知客户模板
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getPayOKTemplate(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_177547763");
        smsTemplate.setParam(param);
        return smsTemplate;
    }

    /**
     * 接受预约模板
     * 您好，您预约的服务已为您安排保洁，保洁人员电话${phone},将会于${date}联系您
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getAcceptReservations(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_176913804");
        smsTemplate.setParam(param);
        return smsTemplate;
    }

    /**
     * 指派人员
     * 您有新的服务安排，订单号：${orderid}，联系人：${name}，联系方式：${number}，请您及时与对方进行联系！
     * @param param
     * @return
     */
    public static SendMessageParam.SmsTemplate getAssignedPersonnel(HashMap<String, String> param){
        SendMessageParam.SmsTemplate smsTemplate= new SendMessageParam.SmsTemplate();
        smsTemplate.setTemplateCode("SMS_180050368");
        smsTemplate.setParam(param);
        return smsTemplate;
    }
}
