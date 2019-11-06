package com.haige.integration.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/2 0:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserinfoParam {
    private String accessToken;
    private String openid;
}
