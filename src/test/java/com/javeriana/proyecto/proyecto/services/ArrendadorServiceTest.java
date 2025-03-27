package com.javeriana.proyecto.proyecto.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.javeriana.proyecto.proyecto.dto.ArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.exception.AuthenticationException;
import com.javeriana.proyecto.proyecto.exception.EmailExistsException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;
import com.javeriana.proyecto.proyecto.service.ArrendadorService;

@ExtendWith(MockitoExtension.class)
class ArrendadorServiceTest {

    @Mock
    private ArrendadorRepository arrendadorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArrendadorService arrendadorService;

    private Arrendador arrendador;
    private ArrendadorDTO arrendadorDTO;

    @BeforeEach
    void setUp() {
        arrendador = new Arrendador();
        arrendador.setId(1L);
        arrendador.setEmail("test@example.com");
        arrendador.setContrasena("password");
        arrendador.setStatus(0);

        arrendadorDTO = new ArrendadorDTO();
        arrendadorDTO.setId(1L);
        arrendadorDTO.setEmail("test@example.com");
        arrendadorDTO.setContrasena("password");
    }

    @Test
    void testGetArrendadorById() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        ArrendadorDTO result = arrendadorService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetArrendadorByIdNotFound() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> arrendadorService.get(1L));
    }

    @Test
    void testGetAllArrendadores() {
        List<Arrendador> arrendadores = Arrays.asList(arrendador);
        when(arrendadorRepository.findAll()).thenReturn(arrendadores);
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        List<ArrendadorDTO> result = arrendadorService.get();

        assertEquals(1, result.size());
    }

    @Test
    void testSaveArrendador() {
        when(arrendadorRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(modelMapper.map(arrendadorDTO, Arrendador.class)).thenReturn(arrendador);
        when(arrendadorRepository.save(arrendador)).thenReturn(arrendador);
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        ArrendadorDTO result = arrendadorService.save(arrendadorDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveArrendadorEmailExists() {
        when(arrendadorRepository.findByEmail("test@example.com")).thenReturn(Optional.of(arrendador));
        assertThrows(EmailExistsException.class, () -> arrendadorService.save(arrendadorDTO));
    }

    @Test
    void testUpdateArrendador() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(arrendadorDTO, Arrendador.class)).thenReturn(arrendador);
        when(arrendadorRepository.save(arrendador)).thenReturn(arrendador);
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        ArrendadorDTO result = arrendadorService.update(arrendadorDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateArrendadorNotFound() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> arrendadorService.update(arrendadorDTO));
    }

    @Test
    void testDeleteArrendador() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(arrendador));
        doNothing().when(arrendadorRepository).save(any(Arrendador.class));

        assertDoesNotThrow(() -> arrendadorService.delete(1L));
        verify(arrendadorRepository, times(1)).save(any(Arrendador.class));
    }

    @Test
    void testDeleteArrendadorNotFound() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> arrendadorService.delete(1L));
    }

    @Test
    void testAuthenticateSuccess() {
        when(arrendadorRepository.findByEmail("test@example.com")).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        ArrendadorDTO result = arrendadorService.authenticate("test@example.com", "password");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testAuthenticateInvalidEmail() {
        when(arrendadorRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());
        assertThrows(AuthenticationException.class, () -> arrendadorService.authenticate("wrong@example.com", "password"));
    }

    @Test
    void testAuthenticateInvalidPassword() {
        when(arrendadorRepository.findByEmail("test@example.com")).thenReturn(Optional.of(arrendador));
        assertThrows(AuthenticationException.class, () -> arrendadorService.authenticate("test@example.com", "wrongpass"));
    }
}
