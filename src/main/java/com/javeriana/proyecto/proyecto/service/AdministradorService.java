package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.AdminDTO;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.exception.AuthenticationException;
import com.javeriana.proyecto.proyecto.exception.EmailExistsException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    AdministradorRepository adminRepository;
    @Autowired
    ModelMapper modelMapper;

    public AdminDTO get(long id) {
        Administrador administrador = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrador with ID " + id + " not found"));
        return modelMapper.map(administrador, AdminDTO.class);
    }

    public List<AdminDTO> get() {
        List<Administrador> administradors = (List<Administrador>) adminRepository.findAll();
        List<AdminDTO> adminsDTOs = administradors.stream()
                                                .map(administrador -> modelMapper.map(administrador, AdminDTO.class))
                                                .collect(Collectors.toList());
        return adminsDTOs;
    }

    public AdminDTO save(AdminDTO adminDTO) {

        if (adminRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new EmailExistsException("El email ya está en uso");
        }

        Administrador administrador = modelMapper.map(adminDTO, Administrador.class);
        administrador.setStatus(0);
        administrador = adminRepository.save(administrador);
        adminDTO.setId(administrador.getId());
        return adminDTO;
    }

    public AdminDTO update(AdminDTO adminDTO) throws RuntimeException {
        Optional<Administrador> adminOptional = adminRepository.findById(adminDTO.getId());
        if (adminOptional.isEmpty()) {
            throw new NotFoundException("Administrador with ID " + adminDTO.getId() + " not found");
        }
        Administrador administrador = adminOptional.get();
        administrador = modelMapper.map(adminDTO, Administrador.class);
        administrador.setStatus(0);
        administrador = adminRepository.save(administrador);
        return modelMapper.map(administrador, AdminDTO.class);
    }

    public void delete(long id) {
        Administrador administrador = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrador with ID " + id + " no encontrado"));
        administrador.setStatus(1);
        adminRepository.save(administrador);
    }

    public AdminDTO authenticate(String email, String contrasena) {
        Optional<Administrador> adminOptional = adminRepository.findByEmail(email);
        
        if (adminOptional.isEmpty()) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        Administrador admin = adminOptional.get();

        if (!contrasena.equals(admin.getContrasena())) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        return modelMapper.map(admin, AdminDTO.class);
    }

}