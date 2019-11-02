package com.haige.service.dto;

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
public class WXLoginDTO {
    private String code;
    private String appid ;
    private String nickName ;
    private String avatarUrl ;
    private String gender ;

    private String country ;

    private String province ;

    private String city ;

    private String language ;
}
