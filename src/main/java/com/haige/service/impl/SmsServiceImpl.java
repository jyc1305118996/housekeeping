package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.ShortMsgDO;
import com.haige.db.mapper.ShortMsgDOMapper;
import com.haige.integration.SmsClient;
import com.haige.integration.param.SendMessageParam;
import com.haige.integration.model.SendMessageResult;
import com.haige.service.convert.ShortMsgConvertUtils;
import com.haige.service.dto.SendSmsDTO;
import com.haige.util.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;


/**
 * @author Archie
 * @date 2019/10/18 1:09
 */
@Service
@Slf4j
public class SmsServiceImpl implements com.haige.service.SmsService {
    @Autowired
    private ShortMsgDOMapper shortMsgDOMapper;

    @Autowired
    private SmsClient smsClient;

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
    public Mono<ResultInfo<String>> sendSms(Mono<SendSmsDTO> sendSmsDTO, Mono<WebSession> webSessionMono) {
        return sendSmsDTO
                .map(smsDTO -> {
                    SendMessageParam sendMessageParam = new SendMessageParam();
                    sendMessageParam.setIphone(smsDTO.getIphone());
                    HashMap<String, String> param = new HashMap<>();
                    String code = RandomStringUtils.random(6, false, true);
                    param.put("code", code);
                    SendMessageParam.SmsTemplate smsTemplate = ShortMsgConvertUtils.getSecurityCodeTemplate(param);
                    sendMessageParam.setSmsTemplate(smsTemplate);
                    SendMessageResult sendMessageResult = smsClient.sendMessage(sendMessageParam);
                    if ("success".equals(sendMessageResult.getSmsStatus())) {
                        log.info("验证码发送成功:iphone:{},message:{}", smsDTO.getIphone(), code);
                        webSessionMono.subscribe(webSession -> {
                            webSession.getAttributes().put(smsDTO.getIphone(), code);
                            TimerUtils.schedule(
                                    new TimerTask() {
                                        @Override
                                        public void run() {
                                            Object code = webSession.getAttributes().get(smsDTO.getIphone());
                                            webSession.getAttributes().remove(smsDTO.getIphone());
                                            log.info("手机号:{},验证码:{};移除成功", smsDTO.getIphone(), code);
                                        }
                                    }
                            );
                        });
                    }
                    ShortMsgDO shortMsgDO = ShortMsgConvertUtils.toDo(smsDTO);
                    shortMsgDO.setSmiBadReason(sendMessageResult.getBadReason());
                    shortMsgDO.setSmiState(sendMessageResult.getSmsStatus());
                    shortMsgDO.setSmiContent(code);
                    shortMsgDO.setSmiIp(smsDTO.getIp());
                    shortMsgDO.setSmiReceiverPhone(smsDTO.getIphone());
                    shortMsgDO.setSmiType(smsDTO.getType());
                    return shortMsgDO;
                })
                .map(shortMsgDO -> {
                    shortMsgDOMapper.insertSelective(shortMsgDO);
                    return new ResultInfo<String>(StatusCodeEnum.OK);
                });
    }


    @Override
    public Mono<ShortMsgDO> findList(String ip, String iphone) {
        ShortMsgDO shortMsgDO = shortMsgDOMapper.findList(ip, iphone);
        log.info("当前ip：{},当前手机号:{},最近的一次短信时间:{}", ip, iphone, shortMsgDO != null ? shortMsgDO.getSmiSenderTime() : "");
        return Mono.justOrEmpty(shortMsgDO);
    }
}
