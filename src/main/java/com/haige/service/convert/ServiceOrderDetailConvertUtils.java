package com.haige.service.convert;

import com.haige.db.entity.ServeDetailDO;
import com.haige.service.dto.UpdateServiceOrderDetailDTO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/12/29 22:25
 */
public class ServiceOrderDetailConvertUtils {
    public static ServeDetailDO toDO(UpdateServiceOrderDetailDTO updateServiceOrderDetailDTO){
        ServeDetailDO serveDetailDO = new ServeDetailDO();
        BeanUtils.copyProperties(updateServiceOrderDetailDTO, serveDetailDO);
        serveDetailDO.setServeStartTime(updateServiceOrderDetailDTO.getTime());
        serveDetailDO.setServeStatus(updateServiceOrderDetailDTO.getStatus().toString());
        return serveDetailDO;
    }
}
