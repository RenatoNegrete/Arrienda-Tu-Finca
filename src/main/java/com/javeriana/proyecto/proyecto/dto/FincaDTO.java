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
    
    private String name;
    private String address;
    private String owner;
    private String email;
    private String phone;
    private int status;

}
