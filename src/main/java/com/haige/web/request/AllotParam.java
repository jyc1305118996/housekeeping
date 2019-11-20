package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/20 23:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllotParam {
    @NotNull
    @Length(min = 1)
    private String orderId;
    @NotNull
    @Length(min = 1)
    private String userId;

}
