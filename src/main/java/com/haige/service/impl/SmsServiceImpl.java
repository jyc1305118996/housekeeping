package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.db.entity.ShortMsgDO;
import com.haige.db.mapper.ShortMsgDOMapper;
import com.haige.integration.SmsServiceClient;
import com.haige.integration.dto.SendMessageDto;
import com.haige.integration.dto.SendMessageResponse;
import com.haige.service.SmsService;
import com.haige.service.convert.ShortMsgConvertUtils;
import com.haige.service.dto.SendSmsDTO;
import com.haige.util.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.TimerTask;


/**
 * @author Archie
 * @date 2019/10/18 1:09
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    private ShortMsgDOMapper shortMsgDOMapper;

    @Autowired
    private SmsServiceClient smsServiceClient;

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
                    SendMessageDto sendMessageDto = new SendMessageDto();
                    sendMessageDto.setIphone(smsDTO.getIphone());
                    // todo 验证码
                    sendMessageDto.setMessage("23454");
                    SendMessageResponse sendMessageResponse = smsServiceClient.sendMessage(sendMessageDto);
                    if ("success".equals(sendMessageResponse.getSmsStatus())) {
                        log.info("验证码发送成功:iphone:{},message:{}", smsDTO.getIphone(), sendMessageResponse.getMessage());
                        webSessionMono.subscribe(webSession -> {
                            webSession.getAttributes().put("checkCode", sendMessageResponse.getMessage());
                            TimerUtils.schedule(
                                    new TimerTask() {
                                        @Override
                                        public void run() {
                                            Object code = webSession.getAttributes().get("checkCode");
                                            webSession.getAttributes().remove("checkCode");
                                            log.info("手机号:{},验证码:{};移除成功", smsDTO.getIphone(), code);
                                        }
                                    }
                            );
                        });
                    }
                    ShortMsgDO shortMsgDO = ShortMsgConvertUtils.toDo(smsDTO);
                    shortMsgDO.setSmiBadReason(sendMessageResponse.getBadReason());
                    shortMsgDO.setSmiState(sendMessageResponse.getSmsStatus());
                    shortMsgDO.setSmiContent(sendMessageResponse.getMessage());
                    shortMsgDO.setSmiIp(smsDTO.getIp());
                    shortMsgDO.setSmiReceiverPhone(smsDTO.getIphone());
                    shortMsgDO.setSmiType(smsDTO.getType());
                    return shortMsgDO;
                })
                .map(shortMsgDO -> {
                    shortMsgDOMapper.insertSelective(shortMsgDO);
                    return new ResultInfo<String>(StatusCode.OK);
                });
    }


    @Override
    public Mono<ShortMsgDO> findList(String ip, String iphone) {
        return Mono.justOrEmpty(shortMsgDOMapper.findList(ip, iphone));
    }
}
