package com.javeriana.proyecto.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Administrador> findByEmail(String email);
}
