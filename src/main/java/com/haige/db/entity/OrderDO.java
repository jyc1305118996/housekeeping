package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
    private String orderId;

    private Integer goodsId;

    private String goodsName;

    private String orderStatus;

    private Integer orderCreateUser;

    private Date orderCreateTime;

    private Integer orderStore;

    private Integer orderUpdateUser;

    private Date orderUpdateTime;

    private BigDecimal orderAmount;

    private BigDecimal orderPrice;

    private String couponIds;

    private String orderAddress;

    private Integer orderCount;
}