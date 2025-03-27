package com.javeriana.proyecto.proyecto.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.javeriana.proyecto.proyecto.dto.ArrendadorDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;
import com.javeriana.proyecto.proyecto.service.ArrendadorService;

class ArrendadorServiceTest {

    @InjectMocks
    private ArrendadorService arrendadorService;

    @Mock
    private ArrendadorRepository arrendadorRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExistingArrendador() {
        // Datos de prueba
        Arrendador arrendador = new Arrendador();
        arrendador.setId(1L);
        arrendador.setEmail("test@email.com");

        ArrendadorDTO arrendadorDTO = new ArrendadorDTO();
        arrendadorDTO.setId(1L);
        arrendadorDTO.setEmail("test@email.com");

        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(arrendador, ArrendadorDTO.class)).thenReturn(arrendadorDTO);

        // Llamar al método y verificar el resultado
        ArrendadorDTO result = arrendadorService.get(1L);
        assertEquals(1L, result.getId());
        assertEquals("test@email.com", result.getEmail());
    }

    @Test
    void testGetNonExistingArrendador() {
        // Configuración del mock para lanzar una excepción
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar que la excepción se lanza
        Exception exception = assertThrows(NotFoundException.class, () -> {
            arrendadorService.get(1L);
        });
        assertEquals("Arrendador with ID 1 not found", exception.getMessage());
    }
}
