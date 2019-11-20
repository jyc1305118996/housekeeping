package com.haige.service.convert;

import com.haige.service.dto.SubmitServiceDTO;
import com.haige.web.request.SubmitServiceParam;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/20 23:33
 */
public class ServiceConvertUtils {
    public static SubmitServiceDTO toDTO(SubmitServiceParam submitServiceParam){
        SubmitServiceDTO submitServiceDTO = new SubmitServiceDTO();
        BeanUtils.copyProperties(submitServiceParam, submitServiceDTO);
        return submitServiceDTO;
    }
}
