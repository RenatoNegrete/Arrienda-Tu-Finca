package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.CalificacionAdministradorDTO;
import com.javeriana.proyecto.proyecto.service.CalificacionAdministradorService;

@RestController
@RequestMapping(value = "/api/calif-administrador")
public class CalificacionAdministradorController {

    @Autowired
    private CalificacionAdministradorService calificacionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionAdministradorDTO get(@PathVariable long id) {
        return calificacionService.get(id);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionAdministradorDTO save(@RequestBody CalificacionAdministradorDTO calificacionDTO) {
        return calificacionService.save(calificacionDTO);
    }

    @GetMapping(value = "/administrador/{idAdministrador}")
    public ResponseEntity<List<CalificacionAdministradorDTO>> getCalificacionesByAdministrador(@PathVariable long idAdministrador) {
        return ResponseEntity.ok(calificacionService.getCalificacionesByAdministrador(idAdministrador));
    }

}
