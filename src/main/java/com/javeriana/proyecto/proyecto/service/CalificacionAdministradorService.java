package com.javeriana.proyecto.proyecto.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.CalificacionAdministradorDTO;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.CalificacionAdministrador;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;
import com.javeriana.proyecto.proyecto.repositorios.CalificacionAdministradorRepository;

@Service
public class CalificacionAdministradorService {

    private CalificacionAdministradorRepository calificacionRepository;
    private AdministradorRepository adminRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CalificacionAdministradorService(CalificacionAdministradorRepository calificacionRepository, AdministradorRepository adminRepository, ModelMapper modelMapper) {
        this.calificacionRepository = calificacionRepository;
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    private String notFound = " not found";
    private String adminException = "Administrador with ID ";
    private String calificacionException = "Calificacion with ID";

    public CalificacionAdministradorDTO get(long id) {
        CalificacionAdministrador calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(calificacionException + id + notFound));

        CalificacionAdministradorDTO calificacionDTO = modelMapper.map(calificacion, CalificacionAdministradorDTO.class);
        calificacionDTO.setIdAdministrador(calificacion.getAdministrador() != null ? calificacion.getAdministrador().getId() : null);
        return calificacionDTO;
    }

    public CalificacionAdministradorDTO save(CalificacionAdministradorDTO calificacionDTO) {
        Administrador administrador = adminRepository.findById(calificacionDTO.getIdAdministrador())
                .orElseThrow(() -> new NotFoundException(adminException + calificacionDTO.getIdAdministrador() + notFound));
        
        CalificacionAdministrador calificacion = modelMapper.map(calificacionDTO, CalificacionAdministrador.class);
        calificacion.setAdministrador(administrador);
        calificacion.setStatus(0);
        calificacion = calificacionRepository.save(calificacion);
        calificacionDTO.setId(calificacion.getId());
        return calificacionDTO;
    }

    public List<CalificacionAdministradorDTO> getCalificacionesByAdministrador(long idAdministrador) {
        Administrador administrador = adminRepository.findById(idAdministrador)
                .orElseThrow(() -> new NotFoundException(adminException + idAdministrador + notFound));
        
        List<CalificacionAdministrador> calificaciones = calificacionRepository.findByAdministrador(administrador);

        return calificaciones.stream().map(calificacion -> {
            CalificacionAdministradorDTO calificacionDTO = modelMapper.map(calificacion, CalificacionAdministradorDTO.class);
            calificacionDTO.setIdAdministrador(calificacion.getAdministrador() != null ? calificacion.getAdministrador().getId() : null);
            return calificacionDTO;
        }).toList();
    }

}
