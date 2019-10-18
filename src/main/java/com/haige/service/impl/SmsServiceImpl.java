package com.haige.service.impl;

import com.haige.db.entity.AuthCodeDO;
import com.haige.db.mapper.AuthCodeDOMapper;
import com.haige.service.SmsService;
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
    private AuthCodeDOMapper authCodeDOMapper;

    @Override
    public Flux<AuthCodeDO> findByIphone(String iphone, String date) {
        List<AuthCodeDO> authCodeDOS = authCodeDOMapper.findByIphone(iphone, date);
        log.info("当前手机号：{}, 当前时间：{}的短信发送情况:{}", iphone, date, authCodeDOS);
        return Flux.fromIterable(authCodeDOS);
    }

    @Override
    public Flux<AuthCodeDO> findByIp(String ip, String date) {
        List<AuthCodeDO> authCodeDOS = authCodeDOMapper.findByIp(ip, date);
        log.info("当前ip：{}, 当前时间：{}的短信发送情况:{}", ip, date, authCodeDOS);
        return Flux.fromIterable(authCodeDOS);
    }
}
