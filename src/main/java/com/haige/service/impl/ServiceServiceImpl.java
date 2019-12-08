package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.ServiceOrderStatusEnum;
import com.haige.db.entity.OrderDO;
import com.haige.db.entity.ServeDetailDO;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.db.mapper.ServeDetailDOMapper;
import com.haige.db.mapperExtend.UserBaseDOExtendMapper;
import com.haige.integration.SmsClient;
import com.haige.integration.param.SendMessageParam;
import com.haige.service.ServiceService;
import com.haige.service.convert.ShortMsgConvertUtils;
import com.haige.service.dto.SubmitServiceDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.util.DateUtils;
import com.haige.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Archie
 * @date 2019/11/20 23:31
 */
@Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServeDetailDOMapper serveDetailDOMapper;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private UserBaseDOExtendMapper userBaseDOExtendMapper;


    @Override
    public Mono<ResultInfo> submit(UserBaseDTO userBaseDTO, Mono<SubmitServiceDTO> serviceDTOMono) {
        AtomicReference<OrderDO> orderDOAtomicReference = new AtomicReference<>();
        return serviceDTOMono
                .doOnNext(submitServiceDTO -> {
                    // 查询订单地址,插入预约订单
                    OrderDO orderDO = orderDOMapper.selectByPrimaryKey(submitServiceDTO.getOrderId());
                    if (orderDO.getOrderCount() <= 0){
                        throw new RuntimeException("订单次数不足");
                    }
                    orderDOAtomicReference.set(orderDO);
                    // 此时不关联服务人员
                    ServeDetailDO serveDetailDO = new ServeDetailDO();
                    serveDetailDO.setConcatName(submitServiceDTO.getName());
                    serveDetailDO.setConcatAddress(orderDO.getOrderAddress());
                    serveDetailDO.setConcatIphone(submitServiceDTO.getIphone());
                    serveDetailDO.setOrderId(submitServiceDTO.getOrderId());
                    // 订单创建人，当前登录用户
                    serveDetailDO.setServeCreateUser(userBaseDTO.getUbdId());
                    serveDetailDO.setServeCreateTime(new Date());
                    // 服务时间
                    serveDetailDO.setServeStartTime(TimeUtil.getDate(DateUtils.convertToDateTime(submitServiceDTO.getServiceTime())));
                    serveDetailDO.setServeUpdateTime(new Date());
                    serveDetailDO.setServeStatus(ServiceOrderStatusEnum.CONFIRM.getStatus());
                    serveDetailDOMapper.insertSelective(serveDetailDO);
                    // 将服务次数减1
                    orderDO.setOrderCount(orderDO.getOrderCount() - 1);
                    // 并发会出问题(原子递减)，不考虑
                    orderDOMapper.updateByPrimaryKeySelective(orderDO);
                })
                .doOnNext(submitServiceDTO -> {
                    // 发送管理员短信
                    OrderDO orderDO = orderDOAtomicReference.get();
                    String managerIphone = userBaseDOExtendMapper.findByManagerIphone(orderDO.getOrderId());
                    HashMap<String, String> param = new HashMap<>();
                    param.put("phone", submitServiceDTO.getIphone());
                    param.put("address", orderDO.getOrderAddress());
                    SendMessageParam.SmsTemplate template = ShortMsgConvertUtils.getAppointmentNotifyTemplate(param);
                    SendMessageParam sendMessageParam = new SendMessageParam();
                    sendMessageParam.setSmsTemplate(template);
                    // 管理员电话
                    sendMessageParam.setIphone(managerIphone);
                    sendMessageParam.setType("预约成功通知");
                    smsClient.sendMessage(sendMessageParam);
                })
                .map(submitServiceDTO -> {
                    // 返回订单剩余次数
                    return ResultInfo.buildSuccess(orderDOAtomicReference.get().getOrderCount());
                });
    }
}
