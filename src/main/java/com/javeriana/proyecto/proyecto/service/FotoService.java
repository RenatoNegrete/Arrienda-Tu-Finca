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
    
    FotoRepository fotoRepository;
    ModelMapper modelMapper;

    @Autowired
    public FotoService(FotoRepository fotoRepository, ModelMapper modelMapper) {
        this.fotoRepository = fotoRepository;
        this.modelMapper = modelMapper;
    }

    private String fotoException = "Foto with ID ";

    public FotoDTO get(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fotoException + id + " not found"));
        
        return modelMapper.map(foto, FotoDTO.class);
    }

public List<FotoDTO> get() {
        List<Foto> fotos = (List<Foto>) fotoRepository.findAll();
        List<FotoDTO> fotoDtos = fotos.stream()
                                .map(foto -> modelMapper.map(foto, FotoDTO.class))
                                .toList();
        return fotoDtos;
    }



    public FotoDTO save(FotoDTO fotoDTO) {
        Foto foto = modelMapper.map(fotoDTO, Foto.class);
        foto.setImagenUrl(fotoDTO.getImagenUrl());
        foto = fotoRepository.save(foto);
        fotoDTO.setId(foto.getId());
        return fotoDTO;
    }

    public FotoDTO update(FotoDTO fotoDTO) throws RuntimeException {
        Optional<Foto> fotoOptional = fotoRepository.findById(fotoDTO.getId());
        if (fotoOptional.isEmpty()) {
            throw new NotFoundException(fotoException + fotoDTO.getId() + " not found");
        }
        Foto foto = fotoOptional.get();
        foto = modelMapper.map(fotoDTO, Foto.class);
        foto = fotoRepository.save(foto);
        return modelMapper.map(foto, FotoDTO.class);
    }

    public void delete(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fotoException + id + " no encontrado"));
        foto.setStatus(1);
        fotoRepository.save(foto);
    }

}
