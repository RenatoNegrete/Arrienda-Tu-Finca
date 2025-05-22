package com.javeriana.proyecto.proyecto.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.javeriana.proyecto.proyecto.entidades.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("""
        SELECT t FROM Token t 
        WHERE t.user.id = :userId 
            AND (t.expired = false OR t.revoked = false)
    """)
    List<Token> findAllValidTokensByUserId(@Param("userId") Long userId);

}