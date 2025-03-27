package com.javeriana.proyecto.proyecto.service;

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

import com.javeriana.proyecto.proyecto.dto.PagoDTO;
import com.javeriana.proyecto.proyecto.entidades.Banco;
import com.javeriana.proyecto.proyecto.entidades.Pago;
import com.javeriana.proyecto.proyecto.entidades.Solicitud;
import com.javeriana.proyecto.proyecto.exception.InvalidPaymentException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.BancoRepository;
import com.javeriana.proyecto.proyecto.repositorios.PagoRepository;
import com.javeriana.proyecto.proyecto.repositorios.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private BancoRepository bancoRepository;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoDTO pagoDTO;
    private Banco banco;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        banco = new Banco();
        banco.setId(1L);
        
        solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setValor(100.0);

        pago = new Pago();
        pago.setId(1L);
        pago.setBanco(banco);
        pago.setSolicitud(solicitud);
        pago.setValor(100.0);
        pago.setStatus(0);
        
        pagoDTO = new PagoDTO();
        pagoDTO.setId(1L);
        pagoDTO.setIdBanco(1L);
        pagoDTO.setIdSolicitud(1L);
        pagoDTO.setValor(100.0);
    }

    @Test
    void testGetPagoById_Success() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(modelMapper.map(pago, PagoDTO.class)).thenReturn(pagoDTO);

        PagoDTO result = pagoService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetPagoById_NotFound() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> pagoService.get(1L));
    }

    @Test
    void testGetAllPagos() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago));
        when(modelMapper.map(pago, PagoDTO.class)).thenReturn(pagoDTO);

        List<PagoDTO> result = pagoService.get();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testSavePago_Success() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(pagoDTO, Pago.class)).thenReturn(pago);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        PagoDTO result = pagoService.save(pagoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSavePago_InvalidAmount() {
        pagoDTO.setValor(50.0); // Diferente al valor de la solicitud
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        
        assertThrows(InvalidPaymentException.class, () -> pagoService.save(pagoDTO));
    }

    @Test
    void testUpdatePago_Success() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(pagoDTO, Pago.class)).thenReturn(pago);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
        when(modelMapper.map(pago, PagoDTO.class)).thenReturn(pagoDTO);

        PagoDTO result = pagoService.update(pagoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdatePago_NotFound() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> pagoService.update(pagoDTO));
    }

    @Test
    void testDeletePago_Success() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        pagoService.delete(1L);

        verify(pagoRepository).save(pago);
        assertEquals(1, pago.getStatus());
    }

    @Test
    void testDeletePago_NotFound() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> pagoService.delete(1L));
    }
}
