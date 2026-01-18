package com.project.lay_note_was.lay_note.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;

    @Value("36000000")
    private int jwtExpirationMs;

    public int getExpiration() { return jwtExpirationMs; }

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("36000000") int jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateJwtToken(String userEmail) {
        return Jwts.builder()
                .claim("userEmail", userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenByEmailId(String userName, String userPhone) {
        return Jwts.builder()
                .claim("userName", userName)
                .claim("userPhone", userPhone)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenByEmail(String userEmail) {
        return Jwts.builder()
                .claim("userEmail", userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid JWT token format");
        }
        return bearerToken.substring("Bearer ".length());
    }

    public String getUserEmailFromJwt (String token) {
        Claims claims = getClaims(token);
        return claims.get("userEmail", String.class);
    }

    public String getNameFromJwt (String token) {
        Claims claims = getClaims(token);
        return claims.get("userName", String.class);
    }

    public String getNickNameFromJwt(String token) {
        Claims claims = getClaims(token);
        return claims.get("nickName", String.class);
    }

    public String getProfileImageUrlFromJwt(String token) {
        Claims claims = getClaims(token);
        return claims.get("profileImageUrl", String.class);
    }

    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
