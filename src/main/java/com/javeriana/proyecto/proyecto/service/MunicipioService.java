package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.MunicipioDTO;
import com.javeriana.proyecto.proyecto.entidades.Municipio;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.MunicipioRepository;

@Service
public class MunicipioService {
      @Autowired
    MunicipioRepository municipioRepository;
    @Autowired
    ModelMapper modelMapper;

    
    public MunicipioDTO get(long id) {
        Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Municipio with ID " + id + " not found"));
        return modelMapper.map(municipio, MunicipioDTO.class);
    }

    public List<MunicipioDTO> get() {
        List<Municipio> Municipios = (List<Municipio>) municipioRepository.findAll();
        List<MunicipioDTO> MunicipioDTOs = Municipios.stream()
                                                .map(Municipio -> modelMapper.map(Municipio, MunicipioDTO.class))
                                                .collect(Collectors.toList());
        return MunicipioDTOs;
    }



    public MunicipioDTO save(MunicipioDTO MunicipioDTO) {
        Municipio Municipio = modelMapper.map(MunicipioDTO, Municipio.class);
        Municipio = municipioRepository.save(Municipio);
        MunicipioDTO.setId(Municipio.getId());
        return MunicipioDTO;
    }

    public MunicipioDTO update(MunicipioDTO MunicipioDTO) throws RuntimeException {
        Optional<Municipio> MunicipioOptional = municipioRepository.findById(MunicipioDTO.getId());
        if (MunicipioOptional.isEmpty()) {
            throw new NotFoundException("Municipio with ID " + MunicipioDTO.getId() + " not found");
        }
        Municipio Municipio = MunicipioOptional.get();
        Municipio = modelMapper.map(MunicipioDTO, Municipio.class);
        Municipio = municipioRepository.save(Municipio);
        return modelMapper.map(Municipio, MunicipioDTO.class);
    }

    public void delete(long id) {
        municipioRepository.deleteById(id);
    }

}
