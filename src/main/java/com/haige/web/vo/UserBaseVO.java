package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseVO {
    private String ubdName;

    private String ubdPoliceName;

    private String ubdFixedPhone;

    private String ubdToken;

    private String ubdIsNew;
}