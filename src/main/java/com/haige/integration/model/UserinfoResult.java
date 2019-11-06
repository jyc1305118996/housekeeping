package com.haige.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/2 0:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserinfoResult {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String[] privilege;
    private String unionid;
}
