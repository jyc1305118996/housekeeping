package com.haige.service;

import com.haige.db.entity.ShortMsgDO;
import com.haige.service.dto.SendSmsDTO;
import reactor.core.publisher.Flux;

/**
 * @author Archie
 * @date 2019/10/18 1:09
 */
public interface SmsService {
    /**
     * 根据手机号查询当天的手机号发送量
     * @param iphone
     * @param date
     * @return
     */
    Flux<ShortMsgDO> findByIphone(String iphone, String date);

    /**
     * 根据id查询当前ip当天的发送量
     * @param ip
     * @param date
     * @return
     */
    Flux<ShortMsgDO> findByIp(String ip, String date);

    /**
     * 发送消息实体类
     * @param sendSmsDTO
     */
    void sendSms(SendSmsDTO sendSmsDTO);
}
