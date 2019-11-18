package com.haige.integration;

import com.haige.integration.model.UserinfoResult;
import com.haige.integration.model.WXAccessTokenResult;
import com.haige.integration.param.AccessTokenParam;
import com.haige.integration.param.UserinfoParam;
import reactor.core.publisher.Mono;

/**微信登陆
 * @author Archie
 * @date 2019/11/1 23:44
 */
public interface WXLoginService {
    Mono<WXAccessTokenResult> getAccessToken(Mono<AccessTokenParam> accessTokenParam);
    Mono<UserinfoResult> getUserinfo(Mono<UserinfoParam> userinfoParam);
}
