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

import com.javeriana.proyecto.proyecto.dto.FotoDTO;
import com.javeriana.proyecto.proyecto.entidades.Foto;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.FotoRepository;
import com.javeriana.proyecto.proyecto.service.FotoService;

@ExtendWith(MockitoExtension.class)
class FotoServiceTest {

    @Mock
    private FotoRepository fotoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FotoService fotoService;

    private Foto foto;
    private FotoDTO fotoDTO;

    @BeforeEach
    void setUp() {
        foto = new Foto();
        foto.setId(1L);
        foto.setImagenUrl("http://example.com/foto.jpg");

        fotoDTO = new FotoDTO();
        fotoDTO.setId(1L);
        fotoDTO.setImagenUrl("http://example.com/foto.jpg");
    }

    @Test
    void testGetById() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        when(modelMapper.map(foto, FotoDTO.class)).thenReturn(fotoDTO);

        FotoDTO result = fotoService.get(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fotoService.get(1L));
    }

    @Test
    void testGetAll() {
        List<Foto> fotos = Arrays.asList(foto);
        List<FotoDTO> fotoDTOs = Arrays.asList(fotoDTO);

        when(fotoRepository.findAll()).thenReturn(fotos);
        when(modelMapper.map(foto, FotoDTO.class)).thenReturn(fotoDTO);

        List<FotoDTO> result = fotoService.get();
        assertEquals(1, result.size());
    }

    @Test
    void testSave() {
        when(modelMapper.map(fotoDTO, Foto.class)).thenReturn(foto);
        when(fotoRepository.save(foto)).thenReturn(foto);
        when(modelMapper.map(foto, FotoDTO.class)).thenReturn(fotoDTO);

        FotoDTO result = fotoService.save(fotoDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        when(modelMapper.map(fotoDTO, Foto.class)).thenReturn(foto);
        when(fotoRepository.save(foto)).thenReturn(foto);
        when(modelMapper.map(foto, FotoDTO.class)).thenReturn(fotoDTO);

        FotoDTO result = fotoService.update(fotoDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateNotFound() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> fotoService.update(fotoDTO));
    }

    @Test
    void testDelete() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        doNothing().when(fotoRepository).save(any(Foto.class));

        assertDoesNotThrow(() -> fotoService.delete(1L));
        verify(fotoRepository, times(1)).save(foto);
    }

    @Test
    void testDeleteNotFound() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> fotoService.delete(1L));
    }
}
