package com.mindsync.lostandfound.lost_and_found_backend.config;

import io.jsonwebtoken.*;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "bGzK2t1OlQkZV1hK3zGt9J+gTxAfkGZlPZ5WnXlJekM=\n";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public ResponseEntity<Map<String,String>> generateToken(User user) {
        System.out.println("Generating token for: " + user.getUsername());
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        System.out.println("Generated Token: " + token);

        Map<String,String> response = new HashMap<>();
        response.put("role", String.valueOf(user.getRole()));
        response.put("token",token);
        response.put("name",user.getName());

        return ResponseEntity.ok(response);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
