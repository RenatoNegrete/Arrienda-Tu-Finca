package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.javeriana.proyecto.proyecto.dto.PagoDTO;
import com.javeriana.proyecto.proyecto.entidades.Pago;
import com.javeriana.proyecto.proyecto.repositorios.PagoRepository;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    ModelMapper modelMapper;

public PagoDTO get(long id) {
        Optional<Pago> solicitudOptional = pagoRepository.findById(id);
        PagoDTO pagoDTO = null;
        if (solicitudOptional != null) {
            pagoDTO = modelMapper.map(solicitudOptional.get(), PagoDTO.class);
        }
        return pagoDTO;
    }

    public List<PagoDTO> get() {
        List<Pago> pagos = (List<Pago>) pagoRepository.findAll();
        List<PagoDTO> pagoDtos = pagos.stream()
                                                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                                                .collect(Collectors.toList());
        return pagoDtos;
    }

    public PagoDTO save(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago = pagoRepository.save(pago);
        pagoDTO.setId(pago.getId());
        return pagoDTO;
    }

    public PagoDTO update(PagoDTO pagoDTO) throws RuntimeException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoDTO.getId());
        if (pagoOptional.isEmpty()) {
            throw new RuntimeException("Foto not found");
        }
        Pago pago = pagoOptional.get();
        pago = modelMapper.map(pagoDTO, Pago.class);
        pago = pagoRepository.save(pago);
        return modelMapper.map(pago, PagoDTO.class);
    }

    public void delete(long id) {
        pagoRepository.deleteById(id);
    }

}
