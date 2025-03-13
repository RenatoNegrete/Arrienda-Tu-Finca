package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.AdminDTO;
import com.javeriana.proyecto.proyecto.service.AdministradorService;

@RestController
@RequestMapping(value = "/api/administrador")

public class AdminController {
    
    @Autowired
    private AdministradorService administradorService;

    @CrossOrigin
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<AdminDTO> get() {
        return administradorService.get();
    }
    
    @CrossOrigin
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminDTO get(@PathVariable long id) {
        return administradorService.get(id);
    }

    @CrossOrigin
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminDTO save(@RequestBody AdminDTO adminDTO) throws RuntimeException {
        return administradorService.save(adminDTO);
    }
    
    @CrossOrigin
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminDTO update(@RequestBody AdminDTO adminDTO) throws RuntimeException {
        return administradorService.update(adminDTO);
    }

    @CrossOrigin
    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        administradorService.delete(id);
    }

}

