package com.haige.service.convert;

import com.haige.db.entity.OrderDO;
import com.haige.web.vo.SubmitOrderVo;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/11/11 23:57
 */
public class OrderConvertUtils {
    public static SubmitOrderVo toVO(OrderDO orderDO){
        SubmitOrderVo submitOrderVo = new SubmitOrderVo();
        BeanUtils.copyProperties(orderDO, submitOrderVo);
        return submitOrderVo;
    }
}
