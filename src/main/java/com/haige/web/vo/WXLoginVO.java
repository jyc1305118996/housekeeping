package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 微信登陆请求
 * @author Archie
 * @date 2019/11/1 21:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXLoginVO {
    @NotNull
    @Length(min = 1)
    private String code;
    @NotNull
    @Length(min = 1)
    private String appid ;
}
