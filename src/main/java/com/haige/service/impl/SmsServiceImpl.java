package com.haige.service.impl;

import com.haige.db.entity.ShortMsgDO;
import com.haige.db.mapper.ShortMsgDOMapper;
import com.haige.service.SmsService;
import com.haige.service.dto.SendSmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;


/**
 * @author Archie
 * @date 2019/10/18 1:09
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    private ShortMsgDOMapper shortMsgDOMapper;

    @Override
    public Flux<ShortMsgDO> findByIphone(String iphone, String date) {
        List<ShortMsgDO> authCodeDOS = shortMsgDOMapper.findByIphone(iphone, date);
        log.info("当前手机号：{}, 当前时间：{}的短信发送情况:{}", iphone, date, authCodeDOS);
        return Flux.fromIterable(authCodeDOS);
    }

    @Override
    public Flux<ShortMsgDO> findByIp(String ip, String date) {
        List<ShortMsgDO> authCodeDOS = shortMsgDOMapper.findByIp(ip, date);
        log.info("当前ip：{}, 当前时间：{}的短信发送情况:{}", ip, date, authCodeDOS);
        return Flux.fromIterable(authCodeDOS);
    }

    @Override
    public void sendSms(SendSmsDTO sendSmsDTO) {
        // todo 发送短信
    }
}
