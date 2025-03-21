package com.javeriana.proyecto.proyecto.repositorios;

import com.javeriana.proyecto.proyecto.entidades.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

}
