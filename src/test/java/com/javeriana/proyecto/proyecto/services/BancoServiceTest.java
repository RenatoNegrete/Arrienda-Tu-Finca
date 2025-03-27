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

import com.javeriana.proyecto.proyecto.dto.BancoDTO;
import com.javeriana.proyecto.proyecto.entidades.Banco;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.BancoRepository;
import com.javeriana.proyecto.proyecto.service.BancoService;

@ExtendWith(MockitoExtension.class)
class BancoServiceTest {
    
    @Mock
    private BancoRepository bancoRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private BancoService bancoService;
    
    private Banco banco;
    private BancoDTO bancoDTO;
    
    @BeforeEach
    void setUp() {
        banco = new Banco();
        banco.setId(1L);
        banco.setNombre("Banco Ejemplo");
        
        bancoDTO = new BancoDTO();
        bancoDTO.setId(1L);
        bancoDTO.setNombre("Banco Ejemplo");
    }
    
    @Test
    void testGetBancoById() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));
        when(modelMapper.map(banco, BancoDTO.class)).thenReturn(bancoDTO);
        
        BancoDTO result = bancoService.get(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Banco Ejemplo", result.getNombre());
    }
    
    @Test
    void testGetBancoByIdNotFound() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bancoService.get(1L));
    }
    
    @Test
    void testGetAllBancos() {
        List<Banco> bancos = Arrays.asList(banco);
        when(bancoRepository.findAll()).thenReturn(bancos);
        when(modelMapper.map(banco, BancoDTO.class)).thenReturn(bancoDTO);
        
        List<BancoDTO> result = bancoService.get();
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Banco Ejemplo", result.get(0).getNombre());
    }
    
    @Test
    void testSaveBanco() {
        when(modelMapper.map(bancoDTO, Banco.class)).thenReturn(banco);
        when(bancoRepository.save(banco)).thenReturn(banco);
        when(modelMapper.map(banco, BancoDTO.class)).thenReturn(bancoDTO);
        
        BancoDTO result = bancoService.save(bancoDTO);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    void testUpdateBanco() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));
        when(modelMapper.map(bancoDTO, Banco.class)).thenReturn(banco);
        when(bancoRepository.save(banco)).thenReturn(banco);
        when(modelMapper.map(banco, BancoDTO.class)).thenReturn(bancoDTO);
        
        BancoDTO result = bancoService.update(bancoDTO);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    void testUpdateBancoNotFound() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bancoService.update(bancoDTO));
    }
    
    @Test
    void testDeleteBanco() {
        when(bancoRepository.existsById(1L)).thenReturn(true);
        
        bancoService.delete(1L);
        
        verify(bancoRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDeleteBancoNotFound() {
        when(bancoRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> bancoService.delete(1L));
    }
}

