package com.tagme.tagme_bank_back.web.util;

import com.tagme.tagme_bank_back.domain.model.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "clave_super_secreta_de_minimo_32_caracteres";
    private static final long EXPIRATION_TIME = 7776000000L;

    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Client client) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", client.getId());
        claims.put("username", client.getUsername());

        return Jwts.builder()
                .claims(claims)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public static Long extractClientId(String token) {
        return extractAllClaims(token).get("clientId", Long.class);
    }

    public static LocalDateTime extractExpirationDate(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusSeconds(EXPIRATION_TIME / 1000);
    }
}
