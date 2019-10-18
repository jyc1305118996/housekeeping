package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeDO {
    private Long id;

    private String mobile;

    private String ip;

    private String type;

    private String sendtime;
}