package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.FincaDTO;
import com.javeriana.proyecto.proyecto.entidades.Finca;
import com.javeriana.proyecto.proyecto.repositorios.FincaRepository;

@Service
public class FincaService {

    @Autowired
    FincaRepository fincaRepository;
    @Autowired
    ModelMapper modelMapper;

    public FincaDTO get(long id) {
        Optional<Finca> fincaOptional = fincaRepository.findById(id);
        FincaDTO fincaDTO = null;
        if (fincaOptional != null) {
            fincaDTO = modelMapper.map(fincaOptional.get(), FincaDTO.class);
        }
        return fincaDTO;
    }

    public List<FincaDTO> get() {
        List<Finca> fincas = (List<Finca>) fincaRepository.findAll();
        List<FincaDTO> fincaDTOs = fincas.stream()
                                                .map(finca -> modelMapper.map(finca, FincaDTO.class))
                                                .collect(Collectors.toList());
        return fincaDTOs;
    }

    public FincaDTO save(FincaDTO fincaDTO) {
        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        fincaDTO.setId(finca.getId());
        return fincaDTO;
    }

    public FincaDTO update(FincaDTO fincaDTO) throws RuntimeException {
        fincaDTO = get(fincaDTO.getId());
        if (fincaDTO == null) {
            throw new RuntimeException("Unidentified registry");

        }
        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        fincaDTO = modelMapper.map(finca, FincaDTO.class);
        return fincaDTO;
    }

    public void delete(long id) {
        fincaRepository.deleteById(id);
    }

}
