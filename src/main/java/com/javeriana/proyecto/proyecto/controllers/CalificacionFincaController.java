package com.javeriana.proyecto.proyecto.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.CalificacionFincaDTO;
import com.javeriana.proyecto.proyecto.service.CalificacionFincaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/calif-finca")
public class CalificacionFincaController {

    @Autowired
    private CalificacionFincaService calificacionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionFincaDTO get(@PathVariable long id) {
        return calificacionService.get(id);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CalificacionFincaDTO save(@RequestBody CalificacionFincaDTO calificacionDTO) {
        return calificacionService.save(calificacionDTO);
    }

    @GetMapping(value = "/finca/{idFinca}")
    public ResponseEntity<List<CalificacionFincaDTO>> getCalificacionesByFinca(@PathVariable long idFinca) {
        return ResponseEntity.ok(calificacionService.getCalificacionesByFinca(idFinca));
    }

}
