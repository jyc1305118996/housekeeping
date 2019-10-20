package com.haige.web.convert;

import com.haige.service.dto.ComboDTO;
import com.haige.web.vo.ComboVO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/10/20 17:44
 */
public class ComboConvertUtils {
    public static ComboDTO toDto(ComboVO comboVO){
        ComboDTO comboDTO = new ComboDTO();
        BeanUtils.copyProperties(comboVO, comboDTO);
        return comboDTO;
    }
}
