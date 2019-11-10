package com.haige.web.convert;

import com.haige.service.dto.SubmitOrderDTO;
import com.haige.web.request.SubmitOrderRequest;
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
}
