package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.UsuarioDTO;
import com.javeriana.proyecto.proyecto.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @CrossOrigin
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<UsuarioDTO> get() {
        return usuarioService.get();
    }
      
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTO get(@PathVariable long id) {
        return usuarioService.get(id);
    }

    @CrossOrigin
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public UsuarioDTO save(@RequestBody UsuarioDTO usuarioDTO) throws RuntimeException {
        return usuarioService.save(usuarioDTO);
    }

    @CrossOrigin
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTO update(@RequestBody UsuarioDTO usuarioDTO) throws RuntimeException {
        return usuarioService.update(usuarioDTO);
    }
      
    @CrossOrigin
    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        usuarioService.delete(id);
    }

}
