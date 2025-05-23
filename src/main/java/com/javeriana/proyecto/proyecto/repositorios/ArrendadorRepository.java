package com.javeriana.proyecto.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.Arrendador;

@Repository
public interface ArrendadorRepository extends JpaRepository<Arrendador, Long> {
    Optional<Arrendador> findByEmail(String email);
}
