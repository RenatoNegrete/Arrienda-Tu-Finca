package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.ArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;

@Service

public class ArrendadorService {

    @Autowired
    ArrendadorRepository arrendadorRepository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendadorDTO get(long id) {
        Optional<Arrendador> arrendadorOptional = arrendadorRepository.findById(id);
        ArrendadorDTO arrendadorDTO = null;
        if (arrendadorOptional != null) {
            arrendadorDTO = modelMapper.map(arrendadorOptional.get(), ArrendadorDTO.class);
        }
        return arrendadorDTO;
    }

    public List<ArrendadorDTO> get() {
        List<Arrendador> arrendadores = (List<Arrendador>) arrendadorRepository.findAll();
        List<ArrendadorDTO> arrendadorDTOs = arrendadores.stream()
                                                    .map(Arrendador -> modelMapper.map(Arrendador, ArrendadorDTO.class))
                                                    .collect(Collectors.toList());
        return arrendadorDTOs;
    }

    public ArrendadorDTO save(ArrendadorDTO arrendadorDTO) {
        Arrendador arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        arrendador.setStatus(0);
        arrendador = arrendadorRepository.save(arrendador);
        arrendadorDTO.setId(arrendador.getId());
        return arrendadorDTO;
    }

    public ArrendadorDTO update(ArrendadorDTO arrendadorDTO) {
        Optional<Arrendador> arrendadorOptional = arrendadorRepository.findById(arrendadorDTO.getId());
        if (arrendadorOptional.isEmpty()) {
            throw new NotFoundException("Usuario with ID " + arrendadorDTO.getId() + " not found");
        }
        Arrendador arrendador = arrendadorOptional.get();
        arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        arrendador.setStatus(0);
        arrendador = arrendadorRepository.save(arrendador);
        return modelMapper.map(arrendador, ArrendadorDTO.class);
    }

    public void delete(long id) {
        arrendadorRepository.deleteById(id);
    }

}
