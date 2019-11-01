package com.haige.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/1 23:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXAccessTokenResult {
    private String accessToken;
    private String openid;
}
