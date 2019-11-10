package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
    private Integer orderId;

    private Integer goodsId;

    private String goodsName;

    private String orderStatus;

    private Integer orderCreateUser;

    private Date orderCreateTime;

    private Integer orderStore;

    private Integer orderUpdateUser;

    private Date orderUpdateTime;
}