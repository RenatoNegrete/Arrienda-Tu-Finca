package com.javeriana.proyecto.proyecto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private long id;
    private String nombres;
    private String apellidos;
    private String correo;
}