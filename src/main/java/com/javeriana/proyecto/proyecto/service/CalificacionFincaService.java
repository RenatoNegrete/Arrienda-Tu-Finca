package com.javeriana.proyecto.proyecto.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.CalificacionFincaDTO;
import com.javeriana.proyecto.proyecto.entidades.CalificacionFinca;
import com.javeriana.proyecto.proyecto.entidades.Finca;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.CalificacionFincaRepository;
import com.javeriana.proyecto.proyecto.repositorios.FincaRepository;

@Service
public class CalificacionFincaService {

    CalificacionFincaRepository calificacionRepository;
    FincaRepository fincaRepository;
    ModelMapper modelMapper;

    @Autowired
    public CalificacionFincaService(CalificacionFincaRepository calificacionRepository, FincaRepository fincaRepository, ModelMapper modelMapper) {
        this.calificacionRepository = calificacionRepository;
        this.fincaRepository = fincaRepository;
        this.modelMapper = modelMapper;
    }

    private String notFound = " not found";
    private String fincaException = "Finca with ID ";
    private String calificacionException = "Calificacion with ID";

    public CalificacionFincaDTO get(long id) {
        CalificacionFinca calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(calificacionException + id + notFound));

        CalificacionFincaDTO calificacionDTO = modelMapper.map(calificacion, CalificacionFincaDTO.class);
        calificacionDTO.setIdFinca(calificacion.getFinca() != null ? calificacion.getFinca().getId() : null);
        return calificacionDTO;
    }

    public CalificacionFincaDTO save(CalificacionFincaDTO calificacionDTO) {
        Finca finca = fincaRepository.findById(calificacionDTO.getIdFinca())
                .orElseThrow(() -> new NotFoundException(fincaException + calificacionDTO.getIdFinca() + notFound));
        
        CalificacionFinca calificacion = modelMapper.map(calificacionDTO, CalificacionFinca.class);
        calificacion.setFinca(finca);
        calificacion.setStatus(0);
        calificacion = calificacionRepository.save(calificacion);
        calificacionDTO.setId(calificacion.getId());
        return calificacionDTO;
    }

    public List<CalificacionFincaDTO> getCalificacionesByFinca(long idFinca) {
        Finca finca = fincaRepository.findById(idFinca)
                .orElseThrow(() -> new NotFoundException(fincaException + idFinca + notFound));
        
        List<CalificacionFinca> calificaciones = calificacionRepository.findByFinca(finca);

        return calificaciones.stream().map(calificacion -> {
            CalificacionFincaDTO calificacionDTO = modelMapper.map(calificacion, CalificacionFincaDTO.class);
            calificacionDTO.setIdFinca(calificacion.getFinca() != null ? calificacion.getFinca().getId() : null);
            return calificacionDTO;
        }).toList();
    }

}
