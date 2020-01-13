package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDO {
    private Integer ucId;

    private Integer ucUserId;

    private String ucUserName;

    private BigDecimal ucCouponPrice;

    private Integer ucCouponId;

    private String ucCouponValidity;

    private String ucIsUse;

}