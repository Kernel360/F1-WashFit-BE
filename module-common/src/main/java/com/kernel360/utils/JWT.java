package com.kernel360.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWT {
    private static final Key SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    //private static final long EXPIRATION_TIME = (long) 1000 * 60 * 15; //15분

    private static final long EXPIRATION_TIME = (long) 1000 * 60 * 3; //테스트를 위해 3분으로 조정
    
    public String generateToken(String entityId) {
        return Jwts.builder()
                .setIssuer("washpedia")
                .setId(entityId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Date extractTime(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public String ownerId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return claims.getId();
    }

    @SneakyThrows
    public long checkedTime(String requestToken) {
        LocalDateTime requestTime = extractTime(requestToken).toInstant().atZone((ZoneId.systemDefault())).toLocalDateTime();
        LocalDateTime timeNow = LocalDateTime.now();

        return Duration.between(timeNow, requestTime).toMinutes();
    }

}
