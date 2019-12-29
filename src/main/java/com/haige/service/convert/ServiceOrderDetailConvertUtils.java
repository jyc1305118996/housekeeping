package com.haige.service.convert;

import com.haige.db.entity.ServeDetailDO;
import com.haige.service.dto.UpdateOrderDetailDTO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/12/29 22:25
 */
public class ServiceOrderDetailConvertUtils {
    public static ServeDetailDO toDO(UpdateOrderDetailDTO updateOrderDetailDTO){
        ServeDetailDO serveDetailDO = new ServeDetailDO();
        BeanUtils.copyProperties(updateOrderDetailDTO, serveDetailDO);
        serveDetailDO.setServeStartTime(updateOrderDetailDTO.getTime());
        serveDetailDO.setServeStatus(updateOrderDetailDTO.getStatus().toString());
        return serveDetailDO;
    }
}
