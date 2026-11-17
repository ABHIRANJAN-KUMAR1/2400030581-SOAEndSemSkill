package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Generate JWT
    public String generateToken(Long userId, String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                secret.getBytes(StandardCharsets.UTF_8)
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    // Validate JWT
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(
                            secret.getBytes(StandardCharsets.UTF_8)
                    )
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Extract all claims
    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(
                        secret.getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract username
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Extract user ID
    public Long extractUserId(String token) {

        return extractClaims(token)
                .get("userId", Long.class);
    }

    // Extract role
    public String extractRole(String token) {

        return extractClaims(token)
                .get("role", String.class);
    }
}