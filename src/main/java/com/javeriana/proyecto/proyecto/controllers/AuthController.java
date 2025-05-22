package com.javeriana.proyecto.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.entidades.LoginRequest;
import com.javeriana.proyecto.proyecto.entidades.RegisterRequest;
import com.javeriana.proyecto.proyecto.entidades.TokenResponse;
import com.javeriana.proyecto.proyecto.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        final TokenResponse token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest request) {
        final TokenResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authService.refreshToken(authHeader);
    }
    
}
