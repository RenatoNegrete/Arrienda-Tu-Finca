package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.AdminDTO;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;

@Service
public class AdministradorService {
    @Autowired
    AdministradorRepository AdminRepository;
    @Autowired
    ModelMapper modelMapper;

    public AdminDTO get(long id) {
        Optional<Administrador> fincaOptional = AdminRepository.findById(id);
        AdminDTO adminDTO = null;
        if (fincaOptional != null) {
            adminDTO = modelMapper.map(fincaOptional.get(), AdminDTO.class);
        }
        return adminDTO;
    }

    public List<AdminDTO> get() {
        List<Administrador> administradors = (List<Administrador>) AdminRepository.findAll();
        List<AdminDTO> fincaDTOs = administradors.stream()
                                                .map(Administrador -> modelMapper.map(Administrador, AdminDTO.class))
                                                .collect(Collectors.toList());
        return fincaDTOs;
    }

    public AdminDTO save(AdminDTO adminDTO) {
        Administrador administrador = modelMapper.map(adminDTO, Administrador.class);
        administrador.setStatus(0);
        administrador = AdminRepository.save(administrador);
        adminDTO.setId(administrador.getId());
        return adminDTO;
    }

    public AdminDTO update(AdminDTO adminDTO) throws RuntimeException {
        Optional<Administrador> adminOptional = AdminRepository.findById(adminDTO.getId());
        if (adminOptional.isEmpty()) {
            throw new RuntimeException("Finca not found");
        }
        Administrador administrador = adminOptional.get();
        administrador = modelMapper.map(adminDTO, Administrador.class);
        administrador.setStatus(0);
        administrador = AdminRepository.save(administrador);
        return modelMapper.map(administrador, AdminDTO.class);
    }

    public void delete(long id) {
        AdminRepository.deleteById(id);
    }

}