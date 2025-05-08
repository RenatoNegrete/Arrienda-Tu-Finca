package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.ArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.exception.AuthenticationException;
import com.javeriana.proyecto.proyecto.exception.EmailExistsException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;

@Service

public class ArrendadorService {

    ArrendadorRepository arrendadorRepository;
    ModelMapper modelMapper;

    @Autowired
    public ArrendadorService(ArrendadorRepository arrendadorRepository, ModelMapper modelMapper) {
        this.arrendadorRepository = arrendadorRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String arrendadorException = "Arrendador with ID ";

    public ArrendadorDTO get(long id) {
        Arrendador arrendador = arrendadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(arrendadorException + id + " not found"));
        return modelMapper.map(arrendador, ArrendadorDTO.class);
    }

    public List<ArrendadorDTO> get() {
        List<Arrendador> arrendadores = (List<Arrendador>) arrendadorRepository.findAll();
        List<ArrendadorDTO> arrendadorDTOs = arrendadores.stream()
                                                    .map(Arrendador -> modelMapper.map(Arrendador, ArrendadorDTO.class))
                                                    .toList();
        return arrendadorDTOs;
    }

    public ArrendadorDTO save(ArrendadorDTO arrendadorDTO) {

        if (arrendadorRepository.findByEmail(arrendadorDTO.getEmail()).isPresent()) {
            throw new EmailExistsException("El email ya está en uso");
        }

        Arrendador arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);

        arrendador.setContrasena(passwordEncoder.encode(arrendadorDTO.getContrasena()));

        arrendador.setStatus(0);
        arrendador = arrendadorRepository.save(arrendador);
        arrendadorDTO.setId(arrendador.getId());
        return arrendadorDTO;
    }

    public ArrendadorDTO update(ArrendadorDTO arrendadorDTO) {
        Optional<Arrendador> arrendadorOptional = arrendadorRepository.findById(arrendadorDTO.getId());
        if (arrendadorOptional.isEmpty()) {
            throw new NotFoundException(arrendadorException + arrendadorDTO.getId() + " not found");
        }
        Arrendador arrendador = arrendadorOptional.get();
        arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        arrendador.setStatus(0);
        arrendador = arrendadorRepository.save(arrendador);
        return modelMapper.map(arrendador, ArrendadorDTO.class);
    }

    public void delete(long id) {
        Arrendador arrendador = arrendadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(arrendadorException + id + " no encontrado"));
        arrendador.setStatus(1);
        arrendadorRepository.save(arrendador);
    }

    public ArrendadorDTO authenticate(String email, String contrasena) {
        Optional<Arrendador> arrendadorOptional = arrendadorRepository.findByEmail(email);
        
        if (arrendadorOptional.isEmpty()) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        Arrendador arrendador = arrendadorOptional.get();

        if (!contrasena.equals(arrendador.getContrasena())) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        return modelMapper.map(arrendador, ArrendadorDTO.class);
    }

}
