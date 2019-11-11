package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Archie
 * @date 2019/11/10 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderVo {
    private String orderId;

    private String goodsName;

    private String orderStatus;

    private BigDecimal orderAmount;

    private BigDecimal orderPrice;
}
