package com.haige.service.convert;

import com.haige.db.entity.UserBaseDO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.vo.UserBaseVO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/1 21:06
 */
public class UserBaseConvertUtils {
    public static UserBaseDTO toDTO(UserBaseDO userBaseDO){
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        BeanUtils.copyProperties(userBaseDO, userBaseDTO);
        return userBaseDTO;
    }
    public static UserBaseDO toDO(UserBaseDTO userBaseDTO){
        UserBaseDO userBaseDO = new UserBaseDO();
        BeanUtils.copyProperties(userBaseDTO, userBaseDO);
        return userBaseDO;
    }
    public static UserBaseVO toVO(UserBaseDO userBaseDO){
        UserBaseVO userBaseVO = new UserBaseVO();
        BeanUtils.copyProperties(userBaseDO, userBaseVO);
        return userBaseVO;
    }
}
