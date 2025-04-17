package com.javeriana.proyecto.proyecto.repositorios;

import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.Finca;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {
    List<Finca> findByAdministrador(Administrador administrador);
    List<Finca> findByDepartamentoId(Long idDepartamento);
    List<Finca> findByNombre(String nombre);
    List<Finca> findByHabitaciones(int habitaciones);
}
