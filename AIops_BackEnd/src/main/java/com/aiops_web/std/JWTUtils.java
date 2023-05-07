package com.aiops_web.std;

import io.jsonwebtoken.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {
    private static final String JWT_SECRET = "aiopsSystemJwtSecretfggdjhbjbnjkljlkkbhvxdaweqwrdytcucyvub";
    private static final long JWT_TTL = 60 * 60 * 24;

    private static final SignatureAlgorithm alg = SignatureAlgorithm.HS256;

    public static String createJWT(Integer userId, Integer role) {
        // header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        long currentTme = getCUrrentTimeStamp();
        long expriationTime = currentTme + JWT_TTL;

        JwtBuilder jwtBuilder = Jwts.builder()
                // header
                .setHeader(header)
                // payload
                .setClaims(claims)
                .setSubject("login")
                .setExpiration(new Date(expriationTime*1000))
                .setIssuedAt(new Date(currentTme*1000))
                // 设置jti(JWT ID)：是JWT的唯一标识
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(alg, JWT_SECRET);

        return jwtBuilder.compact();
    }

    public static LoginState checkToken(String token) {
        if (token == null)
            return LoginState.CHECKFAIL;

        Jws<Claims> claimsJws;
        // 不需要检查内容 只要看能否正常解析 就行
        try {
            claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            return LoginState.EXPIRE;
        } catch (Exception ex) {
            return LoginState.CHECKFAIL;
        }

        return LoginState.SUCCESS;
    }

    public static long getCUrrentTimeStamp() {
        return Instant.now().getEpochSecond();
    }
}
