package com.javeriana.proyecto.proyecto.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime fechasolicitud;
    private LocalDate fechallegada;
    private LocalDate fechasalida;
    private int cantpersonas;
    private double valor;
    private int estado;

    private Long idArrendador;
    private Long idFinca;
    private Long idPago;
    
}
