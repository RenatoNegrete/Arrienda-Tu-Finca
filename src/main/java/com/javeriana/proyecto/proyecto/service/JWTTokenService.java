package com.javeriana.proyecto.proyecto.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.entidades.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService {

    @Value("${jwt.secret}")
    private String secretKey;
    private long jwtExpiration = 86400000;
    private long refreshExpiration = 604800000;

    public String generarToken(final User user) {
        return buildToken(user, jwtExpiration);
    }

    public String generarRefreshToken(final User user) {
        return buildToken(user, refreshExpiration);
    }

    public String buildToken(final User user, final long expiration) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setClaims(Map.of("nombre", user.getNombre()))
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}