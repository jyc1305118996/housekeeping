package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfoDO {
    private Integer goodsId;

    private String goodsName;

    private String goodsRemarks;

    private String goodsTitle;

    private Byte goodsFreq;

    private Date goodsExpreDate;

    private Byte goodsPersonType;

    private Byte goodsPersonFreq;

    private Byte goodsPersonNum;

    private String goodsAdvise;

    private String goodsActiveDesc;

    private String goodsRestrictDesc;

    private Date goodsCreateTime;

    private Integer goodsCreateUser;

    private Date goodsUpdateTime;

    private Date goodsUpdateUser;

}