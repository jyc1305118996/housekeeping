package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCouponDO {
    private Integer gcId;

    private Integer goodsId;

    private Byte gcType;

    private BigDecimal gcPrice;

    private Byte gcUseCondtion;

    private String gcRemarks;

    private Byte gcIsOverlying;

}