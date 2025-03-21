package com.javeriana.proyecto.proyecto.entidades;

import java.security.Timestamp;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private int status;
    private Date fechaLlegada;
    private Date FechaSalida;
    private int cantPersonas;
    private double valor;

}
