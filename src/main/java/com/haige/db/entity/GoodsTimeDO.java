package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTimeDO {
    private Integer gtId;

    private Integer goodsId;

    private Date startTime;

    private Date endTime;

}