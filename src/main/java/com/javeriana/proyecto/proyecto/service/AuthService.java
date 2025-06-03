package com.javeriana.proyecto.proyecto.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.entidades.User;
import com.javeriana.proyecto.proyecto.exception.EmailExistsException;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;
import com.javeriana.proyecto.proyecto.repositorios.TokenRepository;
import com.javeriana.proyecto.proyecto.repositorios.UserRepository;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.entidades.LoginRequest;
import com.javeriana.proyecto.proyecto.entidades.RegisterRequest;
import com.javeriana.proyecto.proyecto.entidades.Token;
import com.javeriana.proyecto.proyecto.entidades.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    public enum Rol {
        ARRENDADOR, ADMINISTRADOR
    }

    private final AdministradorRepository adminRepository;
    private final ArrendadorRepository arrendadorRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTTokenService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        User user;

        Rol rol = switch (request.getType().toUpperCase()) {
            case "ARRENDADOR" -> Rol.ARRENDADOR;
            case "ADMINISTRADOR" -> Rol.ADMINISTRADOR;
            default -> throw new IllegalArgumentException("Tipo de usuario no válido");
        };
        
       if(rol.equals(Rol.ARRENDADOR)) {
            if (arrendadorRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailExistsException("El email ya está en uso");
            }
            Arrendador arrendador = new Arrendador();
            arrendador.setNombre(request.getNombre());
            arrendador.setApellido(request.getApellido());
            arrendador.setContrasena(passwordEncoder.encode(request.getContrasena()));
            arrendador.setEmail(request.getEmail());
            arrendador.setTelefono(request.getTelefono());

            user = arrendadorRepository.save(arrendador);
       } else {
            if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailExistsException("El email ya está en uso");
            }
            Administrador admin = new Administrador();
            admin.setNombre(request.getNombre());
            admin.setApellido(request.getApellido());
            admin.setContrasena(passwordEncoder.encode(request.getContrasena()));
            admin.setEmail(request.getEmail());
            admin.setTelefono(request.getTelefono());

            user = adminRepository.save(admin);
       }

        String jwtToken = jwtService.generarToken(user);
        String refreshToken = jwtService.generarRefreshToken(user);
        saveUserToken(user, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getContrasena()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String jwtToken = jwtService.generarToken(user);
        String refreshToken = jwtService.generarRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                        .user(user)
                        .token(jwtToken)
                        .tokenType(Token.TokenType.BEARER)
                        .expired(false)
                        .revoked(false)
                        .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidTokensByUserId(user.getId());
        if(!validUserTokens.isEmpty()) {
            for(final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(final String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer Token");
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail == null) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        if(!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final String accesToken = jwtService.generarToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accesToken);
        return new TokenResponse(accesToken, refreshToken);
    }

}
