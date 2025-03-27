package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.FotoDTO;
import com.javeriana.proyecto.proyecto.entidades.Foto;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.FotoRepository;

@Service
public class FotoService {
    
    @Autowired
    FotoRepository fotoRepository;
    @Autowired
    ModelMapper modelMapper;

    public FotoDTO get(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Foto with ID " + id + " not found"));
        FotoDTO fotoDTO = modelMapper.map(foto, FotoDTO.class);

        return fotoDTO;
    }

public List<FotoDTO> get() {
        List<Foto> fotos = (List<Foto>) fotoRepository.findAll();
        List<FotoDTO> fotoDtos = fotos.stream()
                                .map(foto -> modelMapper.map(foto, FotoDTO.class))
                                .collect(Collectors.toList());
        return fotoDtos;
    }



    public FotoDTO save(FotoDTO fotoDTO) {
        Foto foto = modelMapper.map(fotoDTO, Foto.class);
        foto = fotoRepository.save(foto);
        fotoDTO.setId(foto.getId());
        return fotoDTO;
    }

    public FotoDTO update(FotoDTO fotoDTO) throws RuntimeException {
        Optional<Foto> fotoOptional = fotoRepository.findById(fotoDTO.getId());
        if (fotoOptional.isEmpty()) {
            throw new NotFoundException("Foto with ID " + fotoDTO.getId() + " not found");
        }
        Foto foto = fotoOptional.get();
        foto = modelMapper.map(fotoDTO, Foto.class);
        foto = fotoRepository.save(foto);
        return modelMapper.map(foto, FotoDTO.class);
    }

    public void delete(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Foto with ID " + id + " no encontrado"));
        foto.setStatus(1);
        fotoRepository.save(foto);
    }

}
