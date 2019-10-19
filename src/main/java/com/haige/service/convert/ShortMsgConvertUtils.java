package com.haige.service.convert;

import com.haige.db.entity.ShortMsgDO;
import com.haige.service.dto.SendSmsDTO;
import com.haige.util.DateUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/10/19 11:54
 */
public class ShortMsgConvertUtils {
    public static ShortMsgDO toDo(SendSmsDTO sendSmsDTO){
        ShortMsgDO shortMsgDO = new ShortMsgDO();
        BeanUtils.copyProperties(sendSmsDTO, shortMsgDO);
        shortMsgDO.setSmiSenderTime(DateUtils.nowOfDateTime());
        return shortMsgDO;
    }
}
