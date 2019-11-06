package com.haige.service;

import com.haige.HaigeApplication;
import com.haige.integration.WXServiceClient;
import com.haige.integration.model.WXAccessTokenResult;
import com.haige.integration.param.AccessTokenParam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2019/11/2 10:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WXServiceClientTest {
    @Autowired
    private WXServiceClient wxServiceClient;
    @Test
    public void getcode(){
        AccessTokenParam accessTokenParam = new AccessTokenParam();
        accessTokenParam.setCode("021q1wOu0FNsbi1gSxPu0Wk6Ou0q1wOO");
        accessTokenParam.setAppid("wxfa36502f2a933ddf");
        Mono<WXAccessTokenResult> accessToken = wxServiceClient.getAccessToken(Mono.just(accessTokenParam));
        Assert.assertNotNull(accessToken);

    }
}
