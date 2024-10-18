package com.eunwoojeon.kanban.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// JWT 생성 및 검증 클래스
@Component
public class JWTUtil {
    private SecretKey secretKey;

    // spring.jwt.secret = 암호화키
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()); // HS256 알고리즘으로 암호화
    }
    
    // 검증 메소드
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey) // 서버에서 생성된 토큰인지 검증
                .build().parseSignedClaims(token) // 암호화된 claim을 복호화
                .getPayload().get("username", String.class); // username playload를 string type으로 획득
    }
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload().getExpiration().before(new Date());
    }
    
    // JWT 토큰 생성 메소드
    public String createJwt(String username, String role, long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
                .signWith(secretKey) // 사인(암호화)
                .compact();
    }
}
