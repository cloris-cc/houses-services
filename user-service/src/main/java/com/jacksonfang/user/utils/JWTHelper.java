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

    public static String generateToken(Map<String, String> claims) {

        JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(DateUtils.addDays(new Date(), 1));
        claims.forEach(builder::withClaim);
        return builder.sign(algorithm);
    }

    public static Map<String, String> verifyToken(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Map<String, String> resultMap = new HashMap<>();
        claimMap.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }
}
