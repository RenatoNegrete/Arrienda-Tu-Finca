package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.ArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.LoginRequest;
import com.javeriana.proyecto.proyecto.service.ArrendadorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/arrendador")
public class ArrendadorController {

    @Autowired
    ArrendadorService usuarioService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<ArrendadorDTO> get() {
        return usuarioService.get();
    }
      
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrendadorDTO get(@PathVariable long id) {
        return usuarioService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public ArrendadorDTO save(@RequestBody ArrendadorDTO usuarioDTO) throws RuntimeException {
        return usuarioService.save(usuarioDTO);
    }

    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrendadorDTO update(@RequestBody ArrendadorDTO usuarioDTO) throws RuntimeException {
        return usuarioService.update(usuarioDTO);
    }
      
    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        usuarioService.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<ArrendadorDTO> login(@RequestBody LoginRequest request) {
    
        ArrendadorDTO arrendador = usuarioService.authenticate(request.getEmail(), request.getContrasena());
        return ResponseEntity.ok(arrendador);

    }

}
