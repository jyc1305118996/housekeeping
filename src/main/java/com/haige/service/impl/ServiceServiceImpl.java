package com.haige.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.ServiceOrderStatusEnum;
import com.haige.convert.ConvertUtils;
import com.haige.db.entity.OrderDO;
import com.haige.db.entity.ServeDetailDO;
import com.haige.db.entity.ServeDetailInfoDO;
import com.haige.db.mapper.OrderDOMapper;
import com.haige.db.mapper.ServeDetailDOMapper;
import com.haige.db.mapperExtend.ServeDetailDOExtendMapper;
import com.haige.db.mapperExtend.UserBaseDOExtendMapper;
import com.haige.integration.SmsClient;
import com.haige.integration.param.SendMessageParam;
import com.haige.service.ServiceService;
import com.haige.service.convert.ServiceOrderDetailConvertUtils;
import com.haige.service.convert.ShortMsgConvertUtils;
import com.haige.service.dto.ServeDetailInfoDTO;
import com.haige.service.dto.SubmitServiceDTO;
import com.haige.service.dto.UpdateServiceOrderDetailDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.util.DateUtils;
import com.haige.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private ServeDetailDOExtendMapper serveDetailDOExtendMapper;

    @Override
    public Mono<ResultInfo> submit(UserBaseDTO userBaseDTO, Mono<SubmitServiceDTO> serviceDTOMono) {
        AtomicReference<OrderDO> orderDOAtomicReference = new AtomicReference<>();
        return serviceDTOMono
                .doOnNext(submitServiceDTO -> {
                    // 查询订单地址,插入预约订单
                    OrderDO orderDO = orderDOMapper.selectByPrimaryKey(submitServiceDTO.getOrderId());
                    if (orderDO.getOrderCount() <= 0) {
                        throw new RuntimeException("订单次数不足");
                    }
                    orderDOAtomicReference.set(orderDO);
                    // 此时不关联服务人员
                    ServeDetailDO serveDetailDO = new ServeDetailDO();
                    serveDetailDO.setConcatName(submitServiceDTO.getName());
                    serveDetailDO.setConcatAddress(orderDO.getOrderAddress());
                    serveDetailDO.setConcatIphone(submitServiceDTO.getIphone());
                    serveDetailDO.setOrderId(submitServiceDTO.getOrderId());
                    serveDetailDO.setServeCreateUser(1);
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

    @Override
    public Mono<ResultInfo> updateServerDetail(Mono<UpdateServiceOrderDetailDTO> updateOrderDetailDTOMono) {
        return updateOrderDetailDTOMono.map(ServiceOrderDetailConvertUtils::toDO)
                .doOnNext(serveDetailDO -> {
                    serveDetailDOMapper.updateByPrimaryKeySelective(serveDetailDO);
                })
                .map(serveDetailDO -> ResultInfo.buildSuccess("success"));
    }

    @Override
    public Mono<ResultInfo> queryServiceOrderList(int index, int size) {
        PageHelper.startPage(index, size);
        ArrayList<ServeDetailInfoDO> all = serveDetailDOExtendMapper.findAll();
        PageInfo<ServeDetailInfoDO> serveDetailDOPageInfo = new PageInfo<>(all);

        List<ServeDetailInfoDTO> convert = ConvertUtils.convert(all, serveDetailDO -> {
            ServeDetailInfoDTO serveDetailInfoDTO = new ServeDetailInfoDTO();
            BeanUtils.copyProperties(serveDetailDO, serveDetailInfoDTO);
            return serveDetailInfoDTO;
        });
        ResultInfo arrayListResultInfo = ResultInfo.buildSuccess(convert);
        arrayListResultInfo.setCount(serveDetailDOPageInfo.getTotal());
        return Mono.just(arrayListResultInfo);
    }

    @Override
    public Mono<ResultInfo> onComplete(int serviceId) {
        serveDetailDOExtendMapper.onComplete(serviceId);
        return Mono.just(ResultInfo.buildSuccess("success"));
    }
}
