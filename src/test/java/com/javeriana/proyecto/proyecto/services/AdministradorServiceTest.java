package com.javeriana.proyecto.proyecto.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.javeriana.proyecto.proyecto.dto.AdminDTO;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.exception.AuthenticationException;
import com.javeriana.proyecto.proyecto.exception.EmailExistsException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;
import com.javeriana.proyecto.proyecto.service.AdministradorService;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {

    @Mock
    private AdministradorRepository adminRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private AdministradorService administradorService;

    private Administrador admin;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        admin = new Administrador();
        admin.setId(1L);
        admin.setEmail("admin@test.com");
        admin.setContrasena("password123");
        admin.setStatus(0);

        adminDTO = new AdminDTO();
        adminDTO.setId(1L);
        adminDTO.setEmail("admin@test.com");
    }

    @Test
    void testGetById() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = administradorService.get(1L);
        assertEquals(adminDTO.getId(), result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> administradorService.get(1L));
    }

    @Test
    void testGetAll() {
        List<Administrador> admins = Arrays.asList(admin);
        List<AdminDTO> adminsDTO = Arrays.asList(adminDTO);

        when(adminRepository.findAll()).thenReturn(admins);
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        List<AdminDTO> result = administradorService.get();
        assertEquals(1, result.size());
    }

    @Test
    void testSave() {
        when(adminRepository.findByEmail(adminDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(adminDTO, Administrador.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = administradorService.save(adminDTO);
        assertEquals(adminDTO.getEmail(), result.getEmail());
    }

    @Test
    void testSaveEmailExists() {
        when(adminRepository.findByEmail(adminDTO.getEmail())).thenReturn(Optional.of(admin));
        assertThrows(EmailExistsException.class, () -> administradorService.save(adminDTO));
    }

    @Test
    void testUpdate() {
        when(adminRepository.findById(adminDTO.getId())).thenReturn(Optional.of(admin));
        when(modelMapper.map(adminDTO, Administrador.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = administradorService.update(adminDTO);
        assertEquals(adminDTO.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateNotFound() {
        when(adminRepository.findById(adminDTO.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> administradorService.update(adminDTO));
    }

    @Test
    void testDelete() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        administradorService.delete(1L);
        verify(adminRepository).save(admin);
        assertEquals(1, admin.getStatus());
    }

    @Test
    void testDeleteNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> administradorService.delete(1L));
    }

    @Test
    void testAuthenticate() {
        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = administradorService.authenticate("admin@test.com", "password123");
        assertEquals(adminDTO.getEmail(), result.getEmail());
    }

    @Test
    void testAuthenticateInvalidEmail() {
        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.empty());
        assertThrows(AuthenticationException.class, () -> administradorService.authenticate("admin@test.com", "password123"));
    }

    @Test
    void testAuthenticateInvalidPassword() {
        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));
        assertThrows(AuthenticationException.class, () -> administradorService.authenticate("admin@test.com", "wrongpassword"));
    }
}

