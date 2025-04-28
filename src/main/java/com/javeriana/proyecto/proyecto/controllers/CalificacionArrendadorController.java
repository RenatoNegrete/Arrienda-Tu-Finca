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

import com.javeriana.proyecto.proyecto.dto.CalificacionArrendadorDTO;
import com.javeriana.proyecto.proyecto.service.CalificacionArrendadorService;

@RestController
@RequestMapping(value = "/api/calif-arrendador")
public class CalificacionArrendadorController {

    @Autowired
    private CalificacionArrendadorService calificacionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionArrendadorDTO get(@PathVariable long id) {
        return calificacionService.get(id);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionArrendadorDTO save(@RequestBody CalificacionArrendadorDTO calificacionDTO) {
        return calificacionService.save(calificacionDTO);
    }

    @GetMapping(value = "/arrendador/{idArrendador}")
    public ResponseEntity<List<CalificacionArrendadorDTO>> getCalificacionesByArrendador(@PathVariable long idArrendador) {
        return ResponseEntity.ok(calificacionService.getCalificacionesByArrendador(idArrendador));
    }

}
