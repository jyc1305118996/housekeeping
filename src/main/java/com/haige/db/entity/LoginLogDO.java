package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogDO {
    private Long sllId;

    private String slIp;

    private String sllUser;

    private Short sllUserType;

}