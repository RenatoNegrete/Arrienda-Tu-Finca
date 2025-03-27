package com.javeriana.proyecto.proyecto.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.javeriana.proyecto.proyecto.dto.SolicitudDTO;
import com.javeriana.proyecto.proyecto.entidades.*;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.exception.WrongStayException;
import com.javeriana.proyecto.proyecto.repositorios.*;
import com.javeriana.proyecto.proyecto.service.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private ArrendadorRepository arrendadorRepository;

    @Mock
    private FincaRepository fincaRepository;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SolicitudService solicitudService;

    private Solicitud solicitud;
    private SolicitudDTO solicitudDTO;
    private Arrendador arrendador;
    private Finca finca;

    @BeforeEach
    void setUp() {
        arrendador = new Arrendador();
        arrendador.setId(1L);

        finca = new Finca();
        finca.setId(1L);
        finca.setValorNoche(100.0);

        solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setArrendador(arrendador);
        solicitud.setFinca(finca);
        solicitud.setFechasolicitud(LocalDateTime.of(2025, 3, 27, 10, 30));
        solicitud.setFechallegada(LocalDate.of(2025, 4, 1));
        solicitud.setFechasalida(LocalDate.of(2025, 4, 6));
        solicitud.setStatus(0);

        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(1L);
        solicitudDTO.setIdArrendador(1L);
        solicitudDTO.setIdFinca(1L);
        solicitudDTO.setFechallegada(LocalDate.of(2025, 4, 1));
        solicitudDTO.setFechasalida(LocalDate.of(2025, 4, 6));
    }

    @Test
    void testGetSolicitudById_Success() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, SolicitudDTO.class)).thenReturn(solicitudDTO);

        SolicitudDTO result = solicitudService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(solicitudRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSolicitudById_NotFound() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> solicitudService.get(1L));

        assertEquals("Solicitud with ID 1 not found", exception.getMessage());
    }

    @Test
    void testGetAllSolicitudes_Success() {
        when(solicitudRepository.findAll()).thenReturn(Arrays.asList(solicitud));
        when(modelMapper.map(solicitud, SolicitudDTO.class)).thenReturn(solicitudDTO);

        List<SolicitudDTO> result = solicitudService.get();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteSolicitud_Success() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        solicitudService.delete(1L);

        assertEquals(1, solicitud.getStatus());
        verify(solicitudRepository, times(1)).save(solicitud);
    }

    @Test
    void testDeleteSolicitud_NotFound() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> solicitudService.delete(1L));

        assertEquals("Solicitud with ID 1 no encontrado", exception.getMessage());
    }
}
