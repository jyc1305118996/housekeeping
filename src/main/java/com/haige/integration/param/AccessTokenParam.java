package com.haige.integration.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/1 23:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenParam {
    private String appid;
    private String code;
    private String secret;
}
