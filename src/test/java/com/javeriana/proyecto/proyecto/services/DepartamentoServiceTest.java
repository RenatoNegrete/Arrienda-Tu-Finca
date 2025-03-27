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

import com.javeriana.proyecto.proyecto.dto.DepartamentoDTO;
import com.javeriana.proyecto.proyecto.entidades.Departamento;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.DepartamentoRepository;
import com.javeriana.proyecto.proyecto.service.DepartamentoService;

@ExtendWith(MockitoExtension.class)
public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartamentoService departamentoService;

    private Departamento departamento;
    private DepartamentoDTO departamentoDTO;

    @BeforeEach
    void setUp() {
        departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNombre("Departamento Ejemplo");

        departamentoDTO = new DepartamentoDTO();
        departamentoDTO.setId(1L);
        departamentoDTO.setNombre("Departamento Ejemplo");
    }

    @Test
    void testGetById_Success() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(modelMapper.map(departamento, DepartamentoDTO.class)).thenReturn(departamentoDTO);

        DepartamentoDTO result = departamentoService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Departamento Ejemplo", result.getNombre());
    }

    @Test
    void testGetById_NotFound() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> departamentoService.get(1L));
    }

    @Test
    void testGetAll() {
        List<Departamento> departamentos = Arrays.asList(departamento);
        List<DepartamentoDTO> departamentoDTOs = Arrays.asList(departamentoDTO);

        when(departamentoRepository.findAll()).thenReturn(departamentos);
        when(modelMapper.map(departamento, DepartamentoDTO.class)).thenReturn(departamentoDTO);

        List<DepartamentoDTO> result = departamentoService.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Departamento Ejemplo", result.get(0).getNombre());
    }

    @Test
    void testSave() {
        when(modelMapper.map(departamentoDTO, Departamento.class)).thenReturn(departamento);
        when(departamentoRepository.save(departamento)).thenReturn(departamento);
        when(modelMapper.map(departamento, DepartamentoDTO.class)).thenReturn(departamentoDTO);

        DepartamentoDTO result = departamentoService.save(departamentoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_Success() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(modelMapper.map(departamentoDTO, Departamento.class)).thenReturn(departamento);
        when(departamentoRepository.save(departamento)).thenReturn(departamento);
        when(modelMapper.map(departamento, DepartamentoDTO.class)).thenReturn(departamentoDTO);

        DepartamentoDTO result = departamentoService.update(departamentoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_NotFound() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> departamentoService.update(departamentoDTO));
    }

    @Test
    void testDelete() {
        doNothing().when(departamentoRepository).deleteById(1L);
        departamentoService.delete(1L);
        verify(departamentoRepository, times(1)).deleteById(1L);
    }
}

