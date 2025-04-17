package com.javeriana.proyecto.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionAdministradorDTO {

    private long id;
    private int puntuacion;
    private String comentario;

    private Long idAdministrador;

}
