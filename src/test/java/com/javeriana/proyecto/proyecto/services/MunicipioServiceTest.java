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

import com.javeriana.proyecto.proyecto.dto.MunicipioDTO;
import com.javeriana.proyecto.proyecto.entidades.Municipio;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.MunicipioRepository;
import com.javeriana.proyecto.proyecto.service.MunicipioService;

@ExtendWith(MockitoExtension.class)
class MunicipioServiceTest {

    @Mock
    private MunicipioRepository municipioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MunicipioService municipioService;

    private Municipio municipio;
    private MunicipioDTO municipioDTO;

    @BeforeEach
    void setUp() {
        municipio = new Municipio();
        municipio.setId(1L);
        municipio.setNombre("Test Municipio");

        municipioDTO = new MunicipioDTO();
        municipioDTO.setId(1L);
        municipioDTO.setNombre("Test Municipio");
    }

    @Test
    void testGetById() {
        when(municipioRepository.findById(1L)).thenReturn(Optional.of(municipio));
        when(modelMapper.map(municipio, MunicipioDTO.class)).thenReturn(municipioDTO);

        MunicipioDTO result = municipioService.get(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(municipioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> municipioService.get(1L));
    }

    @Test
    void testGetAll() {
        List<Municipio> municipios = Arrays.asList(municipio);

        when(municipioRepository.findAll()).thenReturn(municipios);
        when(modelMapper.map(municipio, MunicipioDTO.class)).thenReturn(municipioDTO);

        List<MunicipioDTO> result = municipioService.get();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdate() {
        when(municipioRepository.findById(1L)).thenReturn(Optional.of(municipio));
        when(modelMapper.map(municipioDTO, Municipio.class)).thenReturn(municipio);
        when(municipioRepository.save(municipio)).thenReturn(municipio);
        when(modelMapper.map(municipio, MunicipioDTO.class)).thenReturn(municipioDTO);

        MunicipioDTO result = municipioService.update(municipioDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateNotFound() {
        when(municipioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> municipioService.update(municipioDTO));
    }

    @Test
    void testDelete() {
        doNothing().when(municipioRepository).deleteById(1L);

        assertDoesNotThrow(() -> municipioService.delete(1L));
        verify(municipioRepository, times(1)).deleteById(1L);
    }
}
