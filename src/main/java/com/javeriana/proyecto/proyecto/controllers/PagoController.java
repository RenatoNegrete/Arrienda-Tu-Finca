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

import com.javeriana.proyecto.proyecto.dto.PagoDTO;
import com.javeriana.proyecto.proyecto.service.PagoService;

@RestController
@RequestMapping(value = "/api/pago")
public class PagoController {
         @Autowired
  private PagoService pagoService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<PagoDTO> get() {
        return pagoService.get();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PagoDTO get(@PathVariable long id) {
        return pagoService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public PagoDTO save(@RequestBody PagoDTO pagoDTO) throws RuntimeException {
        return pagoService.save(pagoDTO);
    }

    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public PagoDTO update(@RequestBody PagoDTO pagoDTO) throws RuntimeException {
        return pagoService.update(pagoDTO);
    }

    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        pagoService.delete(id);
    }
}


