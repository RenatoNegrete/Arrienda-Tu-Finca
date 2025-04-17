package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.FotoDTO;
import com.javeriana.proyecto.proyecto.entidades.Foto;
import com.javeriana.proyecto.proyecto.entidades.Finca;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.FincaRepository;
import com.javeriana.proyecto.proyecto.repositorios.FotoRepository;

@Service
public class FotoService {
    
    FotoRepository fotoRepository;
    FincaRepository fincaRepository;
    ModelMapper modelMapper;

    @Autowired
    public FotoService(FotoRepository fotoRepository, FincaRepository fincaRepository, ModelMapper modelMapper) {
        this.fotoRepository = fotoRepository;
        this.fincaRepository = fincaRepository;
        this.modelMapper = modelMapper;
    }

    private String fincaException = "Finca with ID ";
    private String notFound = " not found";

    private String fotoException = "Foto with ID ";

    public FotoDTO get(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fotoException + id + " not found"));
        
        FotoDTO fotoDTO = modelMapper.map(foto, FotoDTO.class);
        fotoDTO.setIdFinca(foto.getFinca() != null ? foto.getFinca().getId() : null);

        return fotoDTO;
    }

    public List<FotoDTO> get() {
        List<Foto> fotos = (List<Foto>) fotoRepository.findAll();
        
        return fotos.stream().map(foto -> {
            FotoDTO fotoDTO = modelMapper.map(foto, FotoDTO.class);
            fotoDTO.setIdFinca(foto.getFinca() != null ? foto.getFinca().getId() : null);
            return fotoDTO;
        }).toList();
    }

    public FotoDTO save(FotoDTO fotoDTO) {

        Finca finca = fincaRepository.findById(fotoDTO.getIdFinca())
            .orElseThrow(() -> new NotFoundException(fincaException + fotoDTO.getIdFinca() + notFound));

        Foto foto = modelMapper.map(fotoDTO, Foto.class);
        foto.setImagenUrl(fotoDTO.getImagenUrl());
        foto.setFinca(finca);
        foto = fotoRepository.save(foto);
        fotoDTO.setId(foto.getId());
        return fotoDTO;
    }

    public FotoDTO update(FotoDTO fotoDTO) throws RuntimeException {
        Optional<Foto> fotoOptional = fotoRepository.findById(fotoDTO.getId());
        if (fotoOptional.isEmpty()) {
            throw new NotFoundException(fotoException + fotoDTO.getId() + " not found");
        }

        Finca finca = fincaRepository.findById(fotoDTO.getIdFinca())
            .orElseThrow(() -> new NotFoundException(fincaException + fotoDTO.getIdFinca() + notFound));

        Foto foto = fotoOptional.get();
        foto = modelMapper.map(fotoDTO, Foto.class);
        foto.setFinca(finca);
        foto = fotoRepository.save(foto);
        FotoDTO fotoDTO2 = modelMapper.map(foto, FotoDTO.class);
        fotoDTO2.setIdFinca(foto.getFinca().getId());
        return fotoDTO2;
    }

    public void delete(long id) {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fotoException + id + " no encontrado"));
        foto.setStatus(1);
        fotoRepository.save(foto);
    }

    public List<FotoDTO> getFotosByFinca(long idFinca) {
        List<Foto> fotos = fotoRepository.findByFincaId(idFinca);

        return fotos.stream().map(foto -> {
            FotoDTO  fotoDTO = modelMapper.map(foto, FotoDTO.class);
            fotoDTO.setIdFinca(foto.getFinca() != null ? foto.getFinca().getId() : null);
            return fotoDTO;
        }).toList();
    }

}
