package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.OrderDO;
import com.haige.service.OrderService;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.convert.OrderConvertUtils;
import com.haige.web.request.AllotParam;
import com.haige.web.request.PayRequest;
import com.haige.web.request.SubmitOrderRequest;
import com.haige.web.request.UpdateOrderRequest;
import com.haige.web.vo.SubmitOrderVo;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Archie
 * @date 2019/11/10 13:45
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 用户下单接口
     *
     * @return
     */
    @PostMapping(value = "/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public Mono<ResultInfo<SubmitOrderVo>> submit(ServerWebExchange serverWebExchange, @RequestBody @Valid Mono<SubmitOrderRequest> submitOrderRequest) {
        return orderService.submit(serverWebExchange, submitOrderRequest.map(OrderConvertUtils::toDTO));
    }

    @GetMapping(value = "/queryOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public Mono<ResultInfo<List<OrderDO>>> queryOrderList(ServerWebExchange serverWebExchange, @RequestParam(value = "status", required = false) Integer status) {
        if (null == status) {
            status = 0;//状态不传输就默认查所有
        }

        return orderService.queryOrderListByCondition(serverWebExchange, status);
    }

    /**
     * 提交支付接口
     *
     * @return
     */
    @PostMapping(value = "/pay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> pay(ServerWebExchange exchange, @RequestBody @Valid Mono<PayRequest> payRequestMono) {
        return orderService.pay(exchange, payRequestMono.map(OrderConvertUtils::toDTO));
    }


    /**
     * 更新订单状态接口
     *
     * @param orderRequestMono
     * @param exchange
     * @return
     */
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> updateOrder(ServerWebExchange exchange, @RequestBody @Valid Mono<UpdateOrderRequest> orderRequestMono) {
        return orderService.updateOrder(exchange, orderRequestMono.map(OrderConvertUtils::toDTO));
    }


    @PostMapping(value = "/allot", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> allot(@RequestAttribute("user") UserBaseDTO userBaseDTO, @RequestBody @Valid Mono<AllotParam> allotParamMono) {
        return orderService.allot(userBaseDTO,  allotParamMono.map(OrderConvertUtils::toDTO));
    }

  @GetMapping(value = "/countOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<ResultInfo<List<HashMap<String, String>>>> countOrder(ServerWebExchange exchange) {

    return orderService.countOrder(exchange);
  }
}
