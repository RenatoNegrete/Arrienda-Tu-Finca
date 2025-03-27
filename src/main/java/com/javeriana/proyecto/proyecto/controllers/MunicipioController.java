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

import com.javeriana.proyecto.proyecto.dto.MunicipioDTO;
import com.javeriana.proyecto.proyecto.service.MunicipioService;

@RestController
@RequestMapping(value = "/api/municipio")
public class MunicipioController {
    @Autowired
    private MunicipioService MunicipioService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<MunicipioDTO> get() {
        return MunicipioService.get();
    }
    
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MunicipioDTO get(@PathVariable long id) {
        return MunicipioService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public MunicipioDTO save(@RequestBody MunicipioDTO MunicipioDTO) throws RuntimeException {
        return MunicipioService.save(MunicipioDTO);
    }
    
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public MunicipioDTO update(@RequestBody MunicipioDTO MunicipioDTO) throws RuntimeException {
        return MunicipioService.update(MunicipioDTO);
    }

    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        MunicipioService.delete(id);
    }
}
