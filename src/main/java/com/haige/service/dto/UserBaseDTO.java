package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDTO {
    private Integer ubdId;

    private String ubdName;

    private String ubdPass;

    private Date ubdExpreDate;

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

    private String ubdToken;

    private String ubdTokenExpreDate;
}