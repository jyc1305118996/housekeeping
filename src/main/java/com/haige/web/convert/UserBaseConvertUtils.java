package com.haige.web.convert;

import com.haige.service.dto.*;
import com.haige.web.request.BindDingRequest;
import com.haige.web.request.CreateUserRequest;
import com.haige.web.request.LoginRequest;
import com.haige.web.request.UpdateUserRequest;
import com.haige.web.vo.PhoneLoginVO;
import com.haige.web.vo.UserBaseVO;
import com.haige.web.vo.WXLoginVO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/1 21:21
 */
public class UserBaseConvertUtils {
    public static WXLoginDTO toDTO(WXLoginVO wxLoginVO){
        WXLoginDTO wxLoginDTO = new WXLoginDTO();
        BeanUtils.copyProperties(wxLoginVO, wxLoginDTO);
        return wxLoginDTO;
    }

    public static PhoneLoginDTO toDTO(PhoneLoginVO phoneLoginVO){
        PhoneLoginDTO phoneLoginDTO = new PhoneLoginDTO();
        BeanUtils.copyProperties(phoneLoginVO, phoneLoginDTO);
        return phoneLoginDTO;
    }
    public static BindDingDTO toDTO(BindDingRequest bindDingRequest){
        BindDingDTO bindDingDTO = new BindDingDTO();
        BeanUtils.copyProperties(bindDingRequest, bindDingDTO);
        return bindDingDTO;
    }

    public static UserBaseVO toVO(UserBaseDTO userBaseDTO) {
        UserBaseVO userBaseVO = new UserBaseVO();
        BeanUtils.copyProperties(userBaseDTO, userBaseVO);
        return userBaseVO;
    }

    public static LoginDTO toDTO(LoginRequest loginRequest){
        LoginDTO loginDTO = new  LoginDTO();
        BeanUtils.copyProperties(loginRequest, loginDTO);
        return loginDTO;
    }
    public static CreateUserDTO toDTO(CreateUserRequest createUserRequest){
        CreateUserDTO createUserDTO = new  CreateUserDTO();
        BeanUtils.copyProperties(createUserRequest, createUserDTO);
        return createUserDTO;
    }
    public static UpdateUserDTO toDTO(UpdateUserRequest updateUserRequest){
        UpdateUserDTO updateUserDTO = new  UpdateUserDTO();
        BeanUtils.copyProperties(updateUserRequest, updateUserDTO);
        return updateUserDTO;
    }
}

