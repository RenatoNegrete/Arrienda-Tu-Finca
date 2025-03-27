package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.FincaDTO;
import com.javeriana.proyecto.proyecto.service.FincaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value = "/api/finca")
public class FincaController {

    @Autowired
    private FincaService fincaService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<FincaDTO> get() {
        return fincaService.get();
    }
    
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FincaDTO get(@PathVariable long id) {
        return fincaService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public FincaDTO save(@RequestBody FincaDTO fincaDTO) {
        return fincaService.save(fincaDTO);
    }
    
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public FincaDTO update(@RequestBody FincaDTO fincaDTO) {
        return fincaService.update(fincaDTO);
    }

    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        fincaService.delete(id);
    }

    @PostMapping("/admin/{idAdmin}/crear")
    public ResponseEntity<FincaDTO> create(@PathVariable long idAdmin, @RequestBody FincaDTO fincaDTO) {
        return ResponseEntity.ok(fincaService.createFinca(fincaDTO, idAdmin));
    }

    @GetMapping("/admin/{idAdmin}")
    public ResponseEntity<List<FincaDTO>> getFincasByAdmin(@PathVariable long idAdmin) {
        return ResponseEntity.ok(fincaService.getFincasByAdministrador(idAdmin));
    }

    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<List<FincaDTO>> getFincasByDepartamento(@PathVariable Long idDepartamento) {
        return ResponseEntity.ok(fincaService.getFincasByDepartamento(idDepartamento));
    }

}
