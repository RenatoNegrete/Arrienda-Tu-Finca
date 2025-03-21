package com.javeriana.proyecto.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ArrendadorDTO {

    private long id;
    private String nombre;
    private String apellido;
    private int age;
    private String username;
    private String password;
    private String phone;
    private String mail;
    private int status;

}
