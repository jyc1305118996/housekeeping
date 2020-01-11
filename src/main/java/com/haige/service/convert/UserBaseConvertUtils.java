package com.haige.service.convert;

import com.haige.db.entity.UserBaseDO;
import com.haige.service.dto.CreateUserDTO;
import com.haige.service.dto.UpdateUserDTO;
import com.haige.service.dto.UserBaseDTO;
import com.haige.web.vo.UserBaseVO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

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
    public static UserBaseDO toDO(CreateUserDTO  createUserDTO){
        UserBaseDO userBaseDO = new UserBaseDO();
        BeanUtils.copyProperties(createUserDTO, userBaseDO);
        userBaseDO.setUbdIsNew("0");
        userBaseDO.setUbdCrteTime(new Date());
        userBaseDO.setUbdUpdtTime(new Date());
        userBaseDO.setUbdAdmin((short)2);
        return userBaseDO;
    }
    public static UserBaseVO toVO(UserBaseDO userBaseDO){
        UserBaseVO userBaseVO = new UserBaseVO();
        BeanUtils.copyProperties(userBaseDO, userBaseVO);
        return userBaseVO;
    }
    public static UserBaseDO toVO(UpdateUserDTO updateUserDTO){
        UserBaseDO userBaseDO = new UserBaseDO();
        BeanUtils.copyProperties(updateUserDTO, userBaseDO);
        return userBaseDO;
    }
}
