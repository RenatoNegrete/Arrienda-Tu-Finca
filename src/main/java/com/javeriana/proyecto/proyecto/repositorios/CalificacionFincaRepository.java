package com.javeriana.proyecto.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javeriana.proyecto.proyecto.entidades.CalificacionFinca;
import com.javeriana.proyecto.proyecto.entidades.Finca;

@Repository
public interface CalificacionFincaRepository extends JpaRepository<CalificacionFinca, Long> {
    List<CalificacionFinca> findByFinca(Finca finca);
}
