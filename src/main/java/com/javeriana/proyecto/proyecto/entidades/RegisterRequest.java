package com.javeriana.proyecto.proyecto.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    String email;
    String contrasena;
    String type;

    String nombre;
    String apellido;
    String telefono;

}
