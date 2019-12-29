package com.haige.web.convert;

import com.haige.service.dto.UpdateOrderDetailDTO;
import com.haige.web.request.UpdateOrderDetailRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/12/29 22:25
 */
public class ServiceOrderDetailConvertUtils {
    public static UpdateOrderDetailDTO toDTO(UpdateOrderDetailRequest request){
        UpdateOrderDetailDTO updateOrderDetailDTO = new UpdateOrderDetailDTO();
        BeanUtils.copyProperties(request, updateOrderDetailDTO);
        return updateOrderDetailDTO;
    }
}
