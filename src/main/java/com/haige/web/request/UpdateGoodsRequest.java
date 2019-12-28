package com.haige.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Archie
 * @date 2019/11/24 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGoodsRequest {
    @NotNull
    private Integer goodsId;
    @NotNull
    private String goodsName;
    @NotNull
    private String goodsRemarks;
    @NotNull
    private Integer goodsFreq;
    @NotNull
    private String goodsAdvise;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goodsExpreDate;
    @NotNull
    private BigDecimal goodsPrice;
    @NotNull
    private BigDecimal goodsDiscountPrice;

}
