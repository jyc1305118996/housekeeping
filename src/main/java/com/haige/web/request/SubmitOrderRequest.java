package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/10 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderRequest {
    @NotNull
    @Length(min = 1)
    private String goodsId;
    private long[] couponIds;
}
