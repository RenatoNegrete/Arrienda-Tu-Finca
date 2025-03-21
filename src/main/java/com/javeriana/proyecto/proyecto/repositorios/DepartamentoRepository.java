package com.javeriana.proyecto.proyecto.repositorios;

import com.javeriana.proyecto.proyecto.entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}
