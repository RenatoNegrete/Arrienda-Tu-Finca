package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.BancoDTO;
import com.javeriana.proyecto.proyecto.service.BancoService;

@RestController
@RequestMapping(value = "/api/banco")
public class BancoController {
      @Autowired
    BancoService bancoService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<BancoDTO> get() {
        return bancoService.get();
    }
      
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BancoDTO get(@PathVariable long id) {
        return bancoService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public BancoDTO save(@RequestBody BancoDTO usuarioDTO) throws RuntimeException {
        return bancoService.save(usuarioDTO);
    }

    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public BancoDTO update(@RequestBody BancoDTO usuarioDTO) throws RuntimeException {
        return bancoService.update(usuarioDTO);
    }
      
    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        bancoService.delete(id);
    }

}
