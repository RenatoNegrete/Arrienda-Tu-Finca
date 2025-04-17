package com.javeriana.proyecto.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.CalificacionAdministrador;

@Repository
public interface CalificacionAdministradorRepository extends JpaRepository<CalificacionAdministrador, Long> {

}
