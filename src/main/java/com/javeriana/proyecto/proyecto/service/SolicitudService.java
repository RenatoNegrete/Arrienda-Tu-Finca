package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.SolicitudDTO;
import com.javeriana.proyecto.proyecto.entidades.Solicitud;
import com.javeriana.proyecto.proyecto.repositorios.SolicitudRepository;

@Service
public class SolicitudService {
    
    @Autowired
    SolicitudRepository solicitudRepository;
    @Autowired
    ModelMapper modelMapper;

       public SolicitudDTO get(long id) {
        Optional<Solicitud> solicitudOptional = solicitudRepository.findById(id);
        SolicitudDTO solicitudDTO = null;
        if (solicitudOptional != null) {
            solicitudDTO = modelMapper.map(solicitudOptional.get(), SolicitudDTO.class);
        }
        return solicitudDTO;
    }

    public List<SolicitudDTO> get() {
        List<Solicitud> solicitudes = (List<Solicitud>) solicitudRepository.findAll();
        List<SolicitudDTO> solicitudDTOs = solicitudes.stream()
                                                .map(solicitud -> modelMapper.map(solicitud, SolicitudDTO.class))
                                                .collect(Collectors.toList());
        return solicitudDTOs;
    }

    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        Solicitud solicitud = modelMapper.map(solicitudDTO, Solicitud.class);
        solicitud.setStatus(0);
        solicitud = solicitudRepository.save(solicitud);
        solicitudDTO.setId(solicitud.getId());
        return solicitudDTO;
    }

    public SolicitudDTO update(SolicitudDTO solicitudDTO) throws RuntimeException {
        Optional<Solicitud> solicitudOptional = solicitudRepository.findById(solicitudDTO.getId());
        if (solicitudOptional.isEmpty()) {
            throw new RuntimeException("Finca not found");
        }
        Solicitud solicitud = solicitudOptional.get();
        solicitud = modelMapper.map(solicitudDTO, Solicitud.class);
        solicitud.setStatus(0);
        solicitud = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudDTO.class);
    }

    public void delete(long id) {
        solicitudRepository.deleteById(id);
    }

}


