package com.javeriana.proyecto.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FincaDTO {

    private long id;
    private String nombre;
    private String tipoIngreso;
    private String descripcion;
    private int habitaciones;
    private int baños;
    private boolean mascotas;
    private boolean piscina;
    private boolean asador;
    private double valorNoche;
    private String address;
    private String owner;
    private int status;

}
