package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.PagoDTO;
import com.javeriana.proyecto.proyecto.entidades.Banco;
import com.javeriana.proyecto.proyecto.entidades.Pago;
import com.javeriana.proyecto.proyecto.entidades.Solicitud;
import com.javeriana.proyecto.proyecto.exception.InvalidPaymentException;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.BancoRepository;
import com.javeriana.proyecto.proyecto.repositorios.PagoRepository;
import com.javeriana.proyecto.proyecto.repositorios.SolicitudRepository;

@Service
public class PagoService {
    
    PagoRepository pagoRepository;
    BancoRepository bancoRepository;
    SolicitudRepository solicitudRepository;
    ModelMapper modelMapper;

    @Autowired
    public PagoService(PagoRepository pagoRepository, BancoRepository bancoRepository, SolicitudRepository solicitudRepository, ModelMapper modelMapper) {
        this.pagoRepository = pagoRepository;
        this.bancoRepository = bancoRepository;
        this.solicitudRepository = solicitudRepository;
        this.modelMapper = modelMapper;
    }

    private String pagoException = "Pago with ID";
    private String notFound = " not found";

    public PagoDTO get(long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(pagoException + id + notFound));
        PagoDTO pagoDTO = modelMapper.map(pago, PagoDTO.class);
        pagoDTO.setIdBanco(pago.getBanco() != null ? pago.getBanco().getId() : null);
        pagoDTO.setIdSolicitud(pago.getSolicitud() != null ? pago.getSolicitud().getId() : null);

        return pagoDTO;
    }

    public List<PagoDTO> get() {
        List<Pago> pagos = (List<Pago>) pagoRepository.findAll();

        return pagos.stream().map(pago -> {
            PagoDTO pagoDTO = modelMapper.map(pago, PagoDTO.class);
            pagoDTO.setIdBanco(pago.getBanco() != null ? pago.getBanco().getId() : null);
            pagoDTO.setIdSolicitud(pago.getSolicitud() != null ? pago.getSolicitud().getId() : null);
            return pagoDTO;
        }).toList();
    }

    public PagoDTO save(PagoDTO pagoDTO) {

        Banco banco = bancoRepository.findById(pagoDTO.getIdBanco())
                .orElseThrow(() -> new NotFoundException("Banco with ID " + pagoDTO.getIdBanco() + notFound));

        Solicitud solicitud = solicitudRepository.findById(pagoDTO.getIdSolicitud())
            .orElseThrow(() -> new NotFoundException("Solicitud with ID " + pagoDTO.getIdSolicitud() + notFound));

        if (pagoDTO.getValor() != solicitud.getValor()) {
            throw new InvalidPaymentException("El valor del pago no coincide con el valor de la solicitud.");
        }

        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setBanco(banco);
        pago.setSolicitud(solicitud);
        pago.setStatus(0);
        pago = pagoRepository.save(pago);
        pagoDTO.setId(pago.getId());
        return pagoDTO;
    }

    public PagoDTO update(PagoDTO pagoDTO) throws RuntimeException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoDTO.getId());
        if (pagoOptional.isEmpty()) {
            throw new NotFoundException(pagoException + pagoDTO.getId() + notFound);
        }

        Banco banco = bancoRepository.findById(pagoDTO.getIdBanco())
                .orElseThrow(() -> new NotFoundException("Banco with ID " + pagoDTO.getIdBanco() + notFound));

        Solicitud solicitud = solicitudRepository.findById(pagoDTO.getIdSolicitud())
                .orElseThrow(() -> new NotFoundException("Solicitud with ID " + pagoDTO.getIdSolicitud() + notFound));

        Pago pago = pagoOptional.get();
        pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setBanco(banco);
        pago.setSolicitud(solicitud);
        pago = pagoRepository.save(pago);
        return modelMapper.map(pago, PagoDTO.class);
    }

    public void delete(long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(pagoException + id + " no encontrado"));
        pago.setStatus(1);
        pagoRepository.save(pago);
    }

}
