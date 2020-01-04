package com.haige.service;

import com.haige.common.bean.ResultInfo;
import com.haige.service.dto.*;
import com.haige.web.vo.OrderDetailVO;
import com.haige.web.vo.SubmitOrderVo;
import java.util.HashMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Archie
 * @date 2019/11/10 13:56
 */
public interface OrderService {
    /**
     * 统一下单接口
     * @param orderRequestMono
     * @return
     */
    Mono<ResultInfo<SubmitOrderVo>> submit(ServerWebExchange serverWebExchange, Mono<SubmitOrderDTO> orderRequestMono);


    
    /**
    * @description: 根据支付状态查询订单
    * @param: 
    * @return: 返回订单集合
    * @author: huxianming
    * @date: 2019-11-12
    */
    Mono<ResultInfo<List<OrderDetailVO>>> queryOrderListByCondition(ServerWebExchange serverWebExchange, int status);

    /**
     * 统一下单
     * @param payDTOMono
     * @return
     */
    Mono<ResultInfo> pay(ServerWebExchange exchange, Mono<PayDTO> payDTOMono);

    /**
     * 付款后根据付款结果更新订单状态
     * @param orderRequestMono
     * @return
     */
    Mono<ResultInfo> updateOrder(ServerWebExchange webExchange, Mono<UpdateOrderDTO> orderRequestMono);

    /**
     * 给员工分配订单
     * @param allotDTOMono
     * @return
     */
    Mono<ResultInfo> allot(UserBaseDTO userBaseDTO, Mono<AllotDTO> allotDTOMono);


    /**
     * 查询订单统计数量
     * @return
     */
    Mono<ResultInfo<List<HashMap<String,String>>>> countOrder(ServerWebExchange serverWebExchange);


    /**
     * @description: 根据服务状态查询订单
     * @param:
     * @return: 返回订单集合
     * @author: huxianming
     * @date: 2019-11-12
     */
    Mono<ResultInfo<List<OrderDetailVO>>> queryOrderListByDetailsStatus(ServerWebExchange serverWebExchange, int detailsStatus);



}
