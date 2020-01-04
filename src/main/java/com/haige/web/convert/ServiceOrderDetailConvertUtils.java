package com.haige.web.convert;

import com.haige.service.dto.UpdateServiceOrderDetailDTO;
import com.haige.web.request.UpdateServiceOrderDetailRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/12/29 22:25
 */
public class ServiceOrderDetailConvertUtils {
    public static UpdateServiceOrderDetailDTO toDTO(UpdateServiceOrderDetailRequest request){
        UpdateServiceOrderDetailDTO updateServiceOrderDetailDTO = new UpdateServiceOrderDetailDTO();
        BeanUtils.copyProperties(request, updateServiceOrderDetailDTO);
        return updateServiceOrderDetailDTO;
    }
}
