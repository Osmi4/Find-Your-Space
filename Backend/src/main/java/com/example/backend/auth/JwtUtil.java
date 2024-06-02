package com.example.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Component
public class JwtUtil {

    private static final String PUBLIC_KEY = "YBXNDJEx_7_zARXHyrBPXnQGzywmh3lmFA_FRwKfDruWS_R2AQo9Q5CkhhAu36Br"; // Replace with your actual public key

    public static Claims decodeJWT(String jwt) {
        RSAPublicKey publicKey = getPublicKey(PUBLIC_KEY);
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private static RSAPublicKey getPublicKey(String base64PublicKey) {
        try {
            byte[] decoded = Base64.getDecoder().decode(base64PublicKey);
            Key key = Keys.hmacShaKeyFor(decoded);
            return (RSAPublicKey) key;
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode public key", e);
        }
    }
}