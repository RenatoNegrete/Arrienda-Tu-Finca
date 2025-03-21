package com.javeriana.proyecto.proyecto.repositorios;

import com.javeriana.proyecto.proyecto.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

}
