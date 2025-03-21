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

    private String name;
    private String lastName;
    private int age;
    private String document;
    private String username;
    private String password;
    private String phone;
    private String mail;

}
