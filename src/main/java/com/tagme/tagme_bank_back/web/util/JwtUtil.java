package com.tagme.tagme_bank_back.web.util;

import com.tagme.tagme_bank_back.domain.model.Client;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

public class JwtUtil {

    private static final String SECRET_KEY =
            "clave_super_secreta_de_minimo_32_caracteres";
    private static final long EXPIRATION_TIME = 7776000000L;

    private static final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Client client) {

        return Jwts.builder()
                .claim("clientId", client.getId())
                .claim("username", client.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public static Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String extractUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    public static Long extractClientId(String token) {
        return parseClaims(token).get("clientId", Long.class);
    }

    public static LocalDateTime extractExpirationDate(String token) {
        return parseClaims(token)
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusSeconds(EXPIRATION_TIME / 1000);
    }
}
