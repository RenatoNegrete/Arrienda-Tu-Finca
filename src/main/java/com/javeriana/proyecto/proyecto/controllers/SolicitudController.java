package com.javeriana.proyecto.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.proyecto.proyecto.dto.SolicitudDTO;
import com.javeriana.proyecto.proyecto.service.SolicitudService;

@RestController
@RequestMapping(value = "/api/solicitud")
public class SolicitudController {

    
    @Autowired
    private SolicitudService solicitudService;

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    public List<SolicitudDTO> get() {
        return solicitudService.get();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SolicitudDTO get(@PathVariable long id) {
        return solicitudService.get(id);
    }

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public SolicitudDTO save(@RequestBody SolicitudDTO solicitudDTO) throws RuntimeException {
        return solicitudService.save(solicitudDTO);
    }
    
    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public SolicitudDTO update(@RequestBody SolicitudDTO solicitudDTO) throws RuntimeException {
        return solicitudService.update(solicitudDTO);
    }

    @DeleteMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public void delete(@PathVariable long id) {
        solicitudService.delete(id);
    }

    @PostMapping("/arrendador/{idArrendador}/crear")
    public ResponseEntity<SolicitudDTO> create(@PathVariable long idArrendador, @RequestBody SolicitudDTO solicitudDTO) {
        return ResponseEntity.ok(solicitudService.createSolicitud(solicitudDTO, idArrendador));
    }

    @GetMapping("/finca/{idFinca}")
    public ResponseEntity<List<SolicitudDTO>> getSolicitudesByFinca(@PathVariable Long idFinca) {
        return ResponseEntity.ok(solicitudService.getSolicitudesByFinca(idFinca));
    }

    @GetMapping("/arrendador/{idArrendador}")
    public ResponseEntity<List<SolicitudDTO>> getSolicitudesByArrendador(@PathVariable Long idArrendador) {
        return ResponseEntity.ok(solicitudService.getSolicitudesByArrendador(idArrendador));
    }
    
}
