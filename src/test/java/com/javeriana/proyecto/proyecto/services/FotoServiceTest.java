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

import com.javeriana.proyecto.proyecto.dto.FotoDTO;
import com.javeriana.proyecto.proyecto.entidades.Foto;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.FotoRepository;
import com.javeriana.proyecto.proyecto.service.*;

class FotoServiceTest {

    @InjectMocks
    private FotoService fotoService;

    @Mock
    private FotoRepository fotoRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExistingFoto() {
        // Datos de prueba
        Foto foto = new Foto();
        foto.setId(1L);
        foto.setImagenUrl("http://example.com/imagen.jpg");

        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setId(1L);
        fotoDTO.setImagenUrl("http://example.com/imagen.jpg");

        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        when(modelMapper.map(foto, FotoDTO.class)).thenReturn(fotoDTO);

        // Llamar al método y verificar el resultado
        FotoDTO result = fotoService.get(1L);
        assertEquals(1L, result.getId());
        assertEquals("http://example.com/imagen.jpg", result.getImagenUrl());
    }

    @Test
    void testGetNonExistingFoto() {
        // Configuración del mock para lanzar una excepción
        when(fotoRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar que la excepción se lanza
        Exception exception = assertThrows(NotFoundException.class, () -> {
            fotoService.get(1L);
        });
        assertEquals("Foto with ID 1 not found", exception.getMessage());
    }
}
