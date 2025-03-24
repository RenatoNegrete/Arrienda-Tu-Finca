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

import com.javeriana.proyecto.proyecto.dto.DepartamentoDTO;
import com.javeriana.proyecto.proyecto.service.DepartamentoService;

@RestController
@RequestMapping(value = "/api/departamento")
public class DepartamentoController {
    
      @Autowired
    private DepartamentoService DepartamentoService;

    @CrossOrigin
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<DepartamentoDTO> get() {
        return DepartamentoService.get();
    }
    
    @CrossOrigin
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartamentoDTO get(@PathVariable long id) {
        return DepartamentoService.get(id);
    }

    @CrossOrigin
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartamentoDTO save(@RequestBody DepartamentoDTO DepartamentoDTO) throws RuntimeException {
        return DepartamentoService.save(DepartamentoDTO);
    }
    
    @CrossOrigin
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartamentoDTO update(@RequestBody DepartamentoDTO DepartamentoDTO) throws RuntimeException {
        return DepartamentoService.update(DepartamentoDTO);
    }

    @CrossOrigin
    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        DepartamentoService.delete(id);
    }
}
