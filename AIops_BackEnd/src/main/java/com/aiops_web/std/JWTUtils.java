package com.aiops_web.std;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.neo4j.cypher.internal.expressions.In;
import scala.Int;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {
    private static final String JWT_SECRET = "aiopsSystemJwtSecret";
    private static final long JWT_TTL = 1000 * 60 * 60 * 24;

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

        JwtBuilder jwtBuilder = Jwts.builder()
                // header
                .setHeader(header)
                // payload
                .setClaims(claims)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TTL))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置jti(JWT ID)：是JWT的唯一标识
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(alg, JWT_SECRET);

        return jwtBuilder.compact();
    }

    public static boolean checkToken(String token) {
        if (token == null)
            return false;

        Jws<Claims> claimsJws;
        // 不需要检查内容 只要看能否正常解析 就行
        try {
            claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        // 能正常解析 根据时间判断是否失效
        Claims claims = claimsJws.getBody();
        Date current = new Date(System.currentTimeMillis());
        return current.after(new Date((Integer) claims.get("exp")));
    }
}
