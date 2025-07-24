package com.demo.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "secretkey123456";
    private final long   EXPIRATION_TIME =  1000L * 60 * 60; // 1h

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        final Date expiration = getClaims(token).getExpiration();
        return extractedUsername.equals(username) && expiration.after(new Date());
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public UserDetails loadUserByToken(String token) {
        String username = extractUsername(token);
        if (username == null) {
            throw new IllegalArgumentException("Token JWT invalide");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("") // à adapter si nécessaire
                .authorities(Collections.emptyList())
                .build();
    }
}
