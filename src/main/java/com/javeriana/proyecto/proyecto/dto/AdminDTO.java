package com.javeriana.proyecto.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    
    private long id;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String telefono;
    private String email;

}
