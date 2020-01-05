package com.haige.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServeDetailInfoDTO {
    private Integer serveId;

    private String orderId;

    private Integer serveUserId;

    private Integer serveCreateUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serveCreateTime;

    private Integer serveUpdateUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serveUpdateTime;

    private String serveStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serveStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serveEndTime;

    private String concatIphone;

    private String concatName;

    private String concatAddress;
    private String goodsName;
    private String serviceUser;
}