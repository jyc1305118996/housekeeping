package com.haige.web.convert;

import com.haige.service.dto.PayDTO;
import com.haige.service.dto.SubmitOrderDTO;
import com.haige.service.dto.UpdateOrderDTO;
import com.haige.web.request.PayRequest;
import com.haige.web.request.SubmitOrderRequest;
import com.haige.web.request.UpdateOrderRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/10 14:01
 */
public class OrderConvertUtils {
    public static SubmitOrderDTO toDTO(SubmitOrderRequest submitOrderRequest){
        SubmitOrderDTO submitOrderDTO = new SubmitOrderDTO();
        BeanUtils.copyProperties(submitOrderRequest, submitOrderDTO);
        return submitOrderDTO;
    }
    public static PayDTO toDTO(PayRequest payRequest){
        PayDTO payDTO = new PayDTO();
        BeanUtils.copyProperties(payRequest, payDTO);
        return payDTO;
    }
    public static UpdateOrderDTO toDTO(UpdateOrderRequest updateOrderRequest){
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        BeanUtils.copyProperties(updateOrderRequest, updateOrderDTO);
        return updateOrderDTO;
    }
}
