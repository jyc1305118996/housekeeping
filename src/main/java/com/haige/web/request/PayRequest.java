package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/16 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayRequest {
    @NotNull
    private String orderId;
    @NotNull
    @Length(min = 1)
    private String appid;
}
