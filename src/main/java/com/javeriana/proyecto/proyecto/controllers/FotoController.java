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

import com.javeriana.proyecto.proyecto.dto.FotoDTO;
import com.javeriana.proyecto.proyecto.service.FotoService;

@RestController
@RequestMapping(value = "/api/foto")
public class FotoController {
     @Autowired
  private FotoService fotoService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<FotoDTO> get() {
        return fotoService.get();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoDTO get(@PathVariable long id) {
        return fotoService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoDTO save(@RequestBody FotoDTO fotoDTO) throws RuntimeException {
        return fotoService.save(fotoDTO);
    }


    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoDTO update(@RequestBody FotoDTO fotoDTO) throws RuntimeException {
        return fotoService.update(fotoDTO);
    }

    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        fotoService.delete(id);
    }
}
