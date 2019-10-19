package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.ShortMsgDO;
import com.haige.service.dto.SendSmsDTO;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    Mono<ResultInfo<String>> sendSms(Mono<SendSmsDTO> sendSmsDTO, Mono<WebSession> webSessionMono);

    /**
     * 根据手机号或者ip，选出最近的发送短信记录
     * @param ip
     * @param iphone
     * @return
     */
    Mono<ShortMsgDO> findList(String ip, String iphone);
}
