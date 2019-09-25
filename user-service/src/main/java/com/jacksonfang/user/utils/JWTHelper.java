package com.jacksonfang.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 token 和 校验 token 的工具类。
 *
 * @author Jacky
 * Date:   2019/03/16
 * Time:   18:32
 */
public class JWTHelper {

    private static final String SECRET = "session_secret";

    private static final String ISSUER = "jacky";

    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 claims 生成 jwt
     *
     * @param claims 信息载体 payload
     * @return jwt
     */
    public static String generateToken(Map<String, String> claims) {
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(DateUtils.addDays(new Date(), 1));
        claims.forEach(jwtBuilder::withClaim);
        // 使用 jwt sign 方法注册生成 jwt(token)
        return jwtBuilder.sign(algorithm);
    }

    /**
     * 解析 jwt 获取 claims
     *
     * @param token jwt
     * @return claims
     */
    public static Map<String, String> verifyToken(String token) {
        // 使用 jwt verify 方法解析出 jwt 的 claims
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Map<String, String> resultMap = new HashMap<>();
        claimMap.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }
}
