package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/18 21:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    @NotNull
    @Length(min = 1)
    private String orderId;
    @NotNull
    @Length(min = 1)
    private String orderStatus;
}
