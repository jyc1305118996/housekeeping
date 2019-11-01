package com.haige.integration;

import com.haige.integration.model.UserinfoResult;
import com.haige.integration.model.WXAccessTokenResult;
import com.haige.integration.param.AccessTokenParam;
import com.haige.integration.param.UserinfoParam;

/**
 * @author Archie
 * @date 2019/11/1 23:44
 */
public interface WXServiceClient {
    WXAccessTokenResult getAccessToken(AccessTokenParam accessTokenParam);
    UserinfoResult getUserinfo(UserinfoParam userinfoParam);
}
