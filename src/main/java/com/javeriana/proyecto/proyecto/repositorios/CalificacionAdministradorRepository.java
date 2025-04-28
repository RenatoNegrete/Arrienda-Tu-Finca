package com.javeriana.proyecto.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.CalificacionAdministrador;

@Repository
public interface CalificacionAdministradorRepository extends JpaRepository<CalificacionAdministrador, Long> {
    List<CalificacionAdministrador> findByAdministrador(Administrador administrador);
}
