package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboDO {
    private Long comboId;

    private String comboName;

    private String comboDesc;

    private BigDecimal comboPrice;

    private String comboNum;

    private String comboType;

    private String creator;

    private String modifier;

    private String comboInfo;

    private String isDel;

    private String modifyDate;

    private String createDate;

    private String expirationDate;

    private String applauseRate;

    private String comboPict;

}