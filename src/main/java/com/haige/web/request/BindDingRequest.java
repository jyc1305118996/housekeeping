package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/17 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindDingRequest {
    @NotNull
    @Length(min = 1)
    private String iphone;
    @NotNull
    @Length(min = 1)
    private String checkCode;
}
