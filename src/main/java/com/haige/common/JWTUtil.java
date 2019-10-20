package com.haige.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author Archie
 * @date 2019/10/20 18:32
 */
@ConfigurationProperties("jwt.config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class JWTUtil {
    private String key;
    private long ttl;

    /**
     * 生成token
     *
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.ES256, key)
                .claim("roles", roles);
        if (ttl > 0) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + ttl));
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析token
     * @param jwtSign
     * @return
     */
    public Claims parseJwt(String jwtSign) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtSign)
                .getBody();
    }
}
