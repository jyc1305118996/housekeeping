package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseDO {
    private BigDecimal ubdId;

    private String ubdName;

    private String ubdPass;

    private Date ubdExpreDate;

    private BigDecimal ubdDeptId;

    private Date ubdLastTime;

    private String ubdLastIp;

    private Short ubdUse;

    private Short ubdAdmin;

    private String ubdPoliceName;

    private String ubdFixedPhone;

    private Long ubdSeq;

    private BigDecimal ubdCrteUserId;

    private Date ubdCrteTime;

    private BigDecimal ubdUpdtUserId;

    private Date ubdUpdtTime;

    private String ubdWechatId;
}