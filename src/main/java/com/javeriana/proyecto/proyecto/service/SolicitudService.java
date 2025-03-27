package com.javeriana.proyecto.proyecto.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.SolicitudDTO;
import com.javeriana.proyecto.proyecto.entidades.Arrendador;
import com.javeriana.proyecto.proyecto.entidades.Finca;
import com.javeriana.proyecto.proyecto.entidades.Pago;
import com.javeriana.proyecto.proyecto.entidades.Solicitud;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.exception.WrongStayException;
import com.javeriana.proyecto.proyecto.repositorios.ArrendadorRepository;
import com.javeriana.proyecto.proyecto.repositorios.FincaRepository;
import com.javeriana.proyecto.proyecto.repositorios.PagoRepository;
import com.javeriana.proyecto.proyecto.repositorios.SolicitudRepository;

@Service
public class SolicitudService {
    
    @Autowired
    SolicitudRepository solicitudRepository;
    @Autowired
    ArrendadorRepository arrendadorRepository;
    @Autowired
    FincaRepository fincaRepository;
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    ModelMapper modelMapper;

    public SolicitudDTO get(long id) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitud with ID " + id + " not found"));
        
        SolicitudDTO solicitudDTO = modelMapper.map(solicitud, SolicitudDTO.class);
        solicitudDTO.setIdArrendador(solicitud.getArrendador() != null ? solicitud.getArrendador().getId() : null);
        solicitudDTO.setIdFinca(solicitud.getFinca() != null ? solicitud.getFinca().getId() : null);
        solicitudDTO.setIdPago(solicitud.getPago() != null ? solicitud.getPago().getId() : null);

        return solicitudDTO;
    }

    public List<SolicitudDTO> get() {
        List<Solicitud> solicitudes = (List<Solicitud>) solicitudRepository.findAll();
        
        return solicitudes.stream().map(solicitud -> {
            SolicitudDTO solicitudDTO = modelMapper.map(solicitud, SolicitudDTO.class);
            solicitudDTO.setIdArrendador(solicitud.getArrendador() != null ? solicitud.getArrendador().getId() : null);
            solicitudDTO.setIdFinca(solicitud.getFinca() != null ? solicitud.getFinca().getId() : null);
            solicitudDTO.setIdPago(solicitud.getPago() != null ? solicitud.getPago().getId() : null);
            return solicitudDTO;
        }).collect(Collectors.toList());
    }

    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        long diasEstancia = ChronoUnit.DAYS.between(solicitudDTO.getFechallegada(), solicitudDTO.getFechasalida());
        if (diasEstancia <= 0) {
            throw new WrongStayException("La fecha de salida debe ser posterior a la fecha de inicio");
        }
        double valorTotal = diasEstancia * finca.getValorNoche();

        Arrendador arrendador = arrendadorRepository.findById(solicitudDTO.getIdArrendador())
                .orElseThrow(() -> new NotFoundException("Arrendador with ID " + solicitudDTO.getIdArrendador() + " not found"));

        Finca finca = fincaRepository.findById(solicitudDTO.getIdFinca())
                .orElseThrow(() -> new NotFoundException("Finca with ID " + solicitudDTO.getIdFinca() + " not found"));

        Solicitud solicitud = modelMapper.map(solicitudDTO, Solicitud.class);

        solicitud.setArrendador(arrendador);
        solicitud.setFinca(finca);
        solicitud.setPago(null);
        solicitud.setValor(valorTotal);
        solicitud.setEstado(0);
        solicitud.setStatus(0);
        solicitud.setFechasolicitud(LocalDateTime.now());
        solicitud = solicitudRepository.save(solicitud);
        solicitudDTO.setId(solicitud.getId());
        return solicitudDTO;
    }

    public SolicitudDTO update(SolicitudDTO solicitudDTO) throws RuntimeException {
        Optional<Solicitud> solicitudOptional = solicitudRepository.findById(solicitudDTO.getId());
        if (solicitudOptional.isEmpty()) {
            throw new NotFoundException("Solicitud with ID " + solicitudDTO.getId() + " not found");
        }

        Arrendador arrendador = arrendadorRepository.findById(solicitudDTO.getIdArrendador())
                .orElseThrow(() -> new NotFoundException("Arrendador with ID " + solicitudDTO.getIdArrendador() + " not found"));

        Finca finca = fincaRepository.findById(solicitudDTO.getIdFinca())
                .orElseThrow(() -> new NotFoundException("Finca with ID " + solicitudDTO.getIdFinca() + " not found"));

        Pago pago = null;
        if (solicitudDTO.getIdPago() != null) {
            pagoRepository.findById(solicitudDTO.getIdPago())
                .orElseThrow(() -> new NotFoundException("Pago with ID " + solicitudDTO.getIdPago() + " not found"));
        }

        Solicitud solicitud = solicitudOptional.get();
        solicitud = modelMapper.map(solicitudDTO, Solicitud.class);
        solicitud.setArrendador(arrendador);
        solicitud.setFinca(finca);
        solicitud.setPago(pago);
        solicitud.setStatus(0);
        solicitud = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudDTO.class);
    }

    public void delete(long id) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitud with ID " + id + " no encontrado"));
        solicitud.setStatus(1);
        solicitudRepository.save(solicitud);
    }

    public SolicitudDTO createSolicitud(SolicitudDTO solicitudDTO, long idArrendador) {

        Arrendador arrendador = arrendadorRepository.findById(idArrendador)
                .orElseThrow(() -> new NotFoundException("Arrendador with ID " + idArrendador + " not found"));

        Finca finca = fincaRepository.findById(solicitudDTO.getIdFinca())
                .orElseThrow(() -> new NotFoundException("Finca with ID " + solicitudDTO.getIdFinca() + " not found"));

        long diasEstancia = ChronoUnit.DAYS.between(solicitudDTO.getFechallegada(), solicitudDTO.getFechasalida());
        if (diasEstancia <= 0) {
            throw new WrongStayException("La fecha de salida debe ser posterior a la fecha de inicio");
        }
        double valorTotal = diasEstancia * finca.getValorNoche();

        Solicitud solicitud = modelMapper.map(solicitudDTO, Solicitud.class);

        solicitud.setArrendador(arrendador);
        solicitud.setFinca(finca);
        solicitud.setPago(null);
        solicitud.setValor(valorTotal);
        solicitud.setEstado(0);
        solicitud.setStatus(0);
        solicitud.setFechasolicitud(LocalDateTime.now());
        solicitud = solicitudRepository.save(solicitud);
        solicitudDTO.setId(solicitud.getId());
        return solicitudDTO;

    }

    public List<SolicitudDTO> getSolicitudesByArrendador(Long idArrendador) {
        List<Solicitud> solicitudes = solicitudRepository.findByArrendadorId(idArrendador);

        return solicitudes.stream().map(solicitud -> {
            SolicitudDTO solicitudDTO = modelMapper.map(solicitud, SolicitudDTO.class);
            solicitudDTO.setIdArrendador(solicitud.getArrendador() != null ? solicitud.getArrendador().getId() : null);
            solicitudDTO.setIdFinca(solicitud.getFinca() != null ? solicitud.getFinca().getId() : null);
            solicitudDTO.setIdPago(solicitud.getPago() != null ? solicitud.getPago().getId() : null);
            return solicitudDTO;
        }).collect(Collectors.toList());
    }

    public List<SolicitudDTO> getSolicitudesByFinca(Long idFinca) {
        List<Solicitud> solicitudes = solicitudRepository.findByFincaId(idFinca);

        return solicitudes.stream().map(solicitud -> {
            SolicitudDTO solicitudDTO = modelMapper.map(solicitud, SolicitudDTO.class);
            solicitudDTO.setIdArrendador(solicitud.getArrendador() != null ? solicitud.getArrendador().getId() : null);
            solicitudDTO.setIdFinca(solicitud.getFinca() != null ? solicitud.getFinca().getId() : null);
            solicitudDTO.setIdPago(solicitud.getPago() != null ? solicitud.getPago().getId() : null);
            return solicitudDTO;
        }).collect(Collectors.toList());
    }

}