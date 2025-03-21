package com.javeriana.proyecto.proyecto.repositorios;

import com.javeriana.proyecto.proyecto.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

}
