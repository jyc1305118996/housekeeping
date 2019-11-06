package com.haige.web.convert;

import com.haige.service.dto.PhoneLoginDTO;
import com.haige.service.dto.WXLoginDTO;
import com.haige.web.vo.PhoneLoginVO;
import com.haige.web.vo.WXLoginVO;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/1 21:21
 */
public class WXLoginConvertUtils {
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
}

