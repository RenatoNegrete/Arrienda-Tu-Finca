package com.javeriana.proyecto.proyecto.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SolicitudDTO {
    
    private long id;
    private int status;
    private Date fechaLlegada;
    private Date FechaSalida;
    private int cantPersonas;
    private double valor;
    
}
