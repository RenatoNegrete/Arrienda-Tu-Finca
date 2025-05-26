package com.javeriana.proyecto.proyecto.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService {

    @Value("${jwt.secret}")
    private String secretKey;
    private long jwtExpiration = 86400000;
    private long refreshExpiration = 604800000;

    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jwtToken.getSubject();
    }

    public String generarToken(final User user) {
        return buildToken(user, jwtExpiration);
    }

    public String generarRefreshToken(final User user) {
        return buildToken(user, refreshExpiration);
    }

    public String buildToken(final User user, final long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("nombre", user.getNombre());
        claims.put("apellido", user.getApellido());
        claims.put("rol", user instanceof Administrador ? "ADMINISTRADOR" : "ARRENDADOR");

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getEmail()) // el "subject" típicamente es el identificador principal, como el email
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(final String token, final User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jwtToken.getExpiration();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}