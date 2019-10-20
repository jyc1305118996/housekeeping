package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommontDO {
    private Long commontId;

    private Long goodsId;

    private String commontDate;

    private String commentatorId;

    private String commontDesc;

    private String isDel;

    private String modifyDate;

}