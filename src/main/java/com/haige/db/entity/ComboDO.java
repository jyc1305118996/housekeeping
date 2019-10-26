package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboDO {
    private Long comboId;

    private String comboName;

    private String comboDesc;

    private String activityDesc;

    private String comboNum;

    private String applyTo;

    private String comboInfo;

    private String expirationDate;

    private String frequency;

    private String waiter;

    private String serviceTime;

    private String comboPict;

    private BigDecimal comboPrice;

    private String comboType;

    private String creator;

    private String modifier;

    private String isDel;

    private String modifyDate;

    private String createDate;

    private String applauseRate;
}