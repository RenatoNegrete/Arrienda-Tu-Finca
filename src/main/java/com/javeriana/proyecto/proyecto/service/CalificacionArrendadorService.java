package com.javeriana.proyecto.proyecto.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.CalificacionArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.entidades.CalificacionArrendador;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;
import com.javeriana.proyecto.proyecto.repositorios.CalificacionArrendadorRepository;

@Service
public class CalificacionArrendadorService {

    private CalificacionArrendadorRepository calificacionRepository;
    private ArrendadorRepository arrendadorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CalificacionArrendadorService(CalificacionArrendadorRepository calificacionRepository, ArrendadorRepository arrendadorRepository, ModelMapper modelMapper) {
        this.calificacionRepository = calificacionRepository;
        this.arrendadorRepository = arrendadorRepository;
        this.modelMapper = modelMapper;
    }

    private String notFound = " not found";
    private String arrendadorException = "Arrendador with ID ";
    private String calificacionException = "Calificacion with ID";

    public CalificacionArrendadorDTO get(long id) {
        CalificacionArrendador calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(calificacionException + id + notFound));

        CalificacionArrendadorDTO calificacionDTO = modelMapper.map(calificacion, CalificacionArrendadorDTO.class);
        calificacionDTO.setIdArrendador(calificacion.getArrendador() != null ? calificacion.getArrendador().getId() : null);
        return calificacionDTO;
    }

    public CalificacionArrendadorDTO save(CalificacionArrendadorDTO calificacionDTO) {
        Arrendador arrendador = arrendadorRepository.findById(calificacionDTO.getIdArrendador())
                .orElseThrow(() -> new NotFoundException(arrendadorException + calificacionDTO.getIdArrendador() + notFound));
        
        CalificacionArrendador calificacion = modelMapper.map(calificacionDTO, CalificacionArrendador.class);
        calificacion.setArrendador(arrendador);
        calificacion.setStatus(0);
        calificacion = calificacionRepository.save(calificacion);
        calificacionDTO.setId(calificacion.getId());
        return calificacionDTO;
    }

    public List<CalificacionArrendadorDTO> getCalificacionesByArrendador(long idArrendador) {
        Arrendador arrendador = arrendadorRepository.findById(idArrendador)
                .orElseThrow(() -> new NotFoundException(arrendadorException + idArrendador + notFound));
        
        List<CalificacionArrendador> calificaciones = calificacionRepository.findByArrendador(arrendador);

        return calificaciones.stream().map(calificacion -> {
            CalificacionArrendadorDTO calificacionDTO = modelMapper.map(calificacion, CalificacionArrendadorDTO.class);
            calificacionDTO.setIdArrendador(calificacion.getArrendador() != null ? calificacion.getArrendador().getId() : null);
            return calificacionDTO;
        }).toList();
    }

}
