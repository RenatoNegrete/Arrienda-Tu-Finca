package com.javeriana.proyecto.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.CalificacionArrendador;

@Repository
public interface CalificacionArrendadorRepository extends JpaRepository<CalificacionArrendador, Long> {

}
