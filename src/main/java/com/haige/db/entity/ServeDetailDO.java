package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServeDetailDO {
    private Integer serveId;

    private String orderId;

    private Integer serveUserId;

    private Integer serveCreateUser;

    private Date serveCreateTime;

    private Integer serveUpdateUser;

    private Date serveUpdateTime;

    private String serveStatus;

    private Date serveStartTime;

    private Date serveEndTime;

    private String concatIphone;

    private String concatName;

    private String concatAddress;
}