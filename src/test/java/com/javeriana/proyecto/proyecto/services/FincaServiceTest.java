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

import com.javeriana.proyecto.proyecto.dto.FincaDTO;
import com.javeriana.proyecto.proyecto.entidades.*;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.*;
import com.javeriana.proyecto.proyecto.service.FincaService;

@ExtendWith(MockitoExtension.class)
class FincaServiceTest {

    @Mock private FincaRepository fincaRepository;
    @Mock private AdministradorRepository administradorRepository;
    @Mock private DepartamentoRepository departamentoRepository;
    @Mock private MunicipioRepository municipioRepository;
    @Mock private FotoRepository fotoRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private FincaService fincaService;

    private Finca finca;
    private FincaDTO fincaDTO;
    private Administrador administrador;
    private Departamento departamento;
    private Municipio municipio;
    private Foto foto;

    @BeforeEach
    void setUp() {
        administrador = new Administrador();
        administrador.setId(1L);

        departamento = new Departamento();
        departamento.setId(1L);

        municipio = new Municipio();
        municipio.setId(1L);

        foto = new Foto();
        foto.setId(1L);

        finca = new Finca();
        finca.setId(1L);
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setFoto(foto);
        finca.setStatus(0);

        fincaDTO = new FincaDTO();
        fincaDTO.setId(1L);
        fincaDTO.setIdAdministrador(1L);
        fincaDTO.setIdDepartamento(1L);
        fincaDTO.setIdMunicipio(1L);
        fincaDTO.setIdFoto(1L);
    }

    @Test
    void getFincaById_Exists_ReturnsFincaDTO() {
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        when(modelMapper.map(finca, FincaDTO.class)).thenReturn(fincaDTO);

        FincaDTO result = fincaService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getIdAdministrador());
        verify(fincaRepository, times(1)).findById(1L);
    }

    @Test
    void getFincaById_NotExists_ThrowsException() {
        when(fincaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fincaService.get(1L));
        verify(fincaRepository, times(1)).findById(1L);
    }

    @Test
    void getAllFincas_ReturnsList() {
        when(fincaRepository.findAll()).thenReturn(Arrays.asList(finca));
        when(modelMapper.map(finca, FincaDTO.class)).thenReturn(fincaDTO);

        List<FincaDTO> result = fincaService.get();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(fincaRepository, times(1)).findAll();
    }

    @Test
    void saveFinca_Successful_ReturnsFincaDTO() {
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(administrador));
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(municipioRepository.findById(1L)).thenReturn(Optional.of(municipio));
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        when(modelMapper.map(fincaDTO, Finca.class)).thenReturn(finca);
        when(fincaRepository.save(any(Finca.class))).thenReturn(finca);
        when(modelMapper.map(finca, FincaDTO.class)).thenReturn(fincaDTO);

        FincaDTO result = fincaService.save(fincaDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(fincaRepository, times(1)).save(any(Finca.class));
    }

    @Test
    void saveFinca_MissingAdministrador_ThrowsException() {
        when(administradorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fincaService.save(fincaDTO));
        verify(administradorRepository, times(1)).findById(1L);
    }

    @Test
    void updateFinca_Successful_ReturnsUpdatedFincaDTO() {
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(administrador));
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(municipioRepository.findById(1L)).thenReturn(Optional.of(municipio));
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(foto));
        when(modelMapper.map(fincaDTO, Finca.class)).thenReturn(finca);
        when(fincaRepository.save(any(Finca.class))).thenReturn(finca);
        when(modelMapper.map(finca, FincaDTO.class)).thenReturn(fincaDTO);

        FincaDTO result = fincaService.update(fincaDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(fincaRepository, times(1)).save(any(Finca.class));
    }

    @Test
    void updateFinca_NotExists_ThrowsException() {
        when(fincaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fincaService.update(fincaDTO));
        verify(fincaRepository, times(1)).findById(1L);
    }
}
