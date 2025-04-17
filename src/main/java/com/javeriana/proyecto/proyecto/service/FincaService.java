package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.FincaDTO;
import com.javeriana.proyecto.proyecto.entidades.Administrador;
import com.javeriana.proyecto.proyecto.entidades.Departamento;
import com.javeriana.proyecto.proyecto.entidades.Finca;
import com.javeriana.proyecto.proyecto.entidades.Foto;
import com.javeriana.proyecto.proyecto.entidades.Municipio;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.AdministradorRepository;
import com.javeriana.proyecto.proyecto.repositorios.DepartamentoRepository;
import com.javeriana.proyecto.proyecto.repositorios.FincaRepository;
import com.javeriana.proyecto.proyecto.repositorios.FotoRepository;
import com.javeriana.proyecto.proyecto.repositorios.MunicipioRepository;

@Service
public class FincaService {

    FincaRepository fincaRepository;
    AdministradorRepository administradorRepository;
    DepartamentoRepository departamentoRepository;
    MunicipioRepository municipioRepository;
    FotoRepository fotoRepository;
    ModelMapper modelMapper;

    @Autowired
    public FincaService(FincaRepository fincaRepository, AdministradorRepository administradorRepository, DepartamentoRepository departamentoRepository, MunicipioRepository municipioRepository, FotoRepository fotoRepository, ModelMapper modelMapper) {
        this.fincaRepository = fincaRepository;
        this.administradorRepository = administradorRepository;
        this.departamentoRepository = departamentoRepository;
        this.municipioRepository = municipioRepository;
        this.fotoRepository = fotoRepository;
        this.modelMapper = modelMapper;
    }

    private String notFound = " not found";
    private String notEncontrado = " no encontrado";
    private String fincaException = "Finca with ID ";
    private String adminException = "Administrador with ID ";
    private String departamentoException = "Departamento with ID ";
    private String municipioException = "Municipio with ID ";
    private String fotoException = "Foto with ID ";

    public FincaDTO get(long id) {
        Finca finca = fincaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fincaException + id + notFound));
        
        FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
        fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
        fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
        fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);

        return fincaDTO;
    }

    public List<FincaDTO> get() {
        List<Finca> fincas = (List<Finca>) fincaRepository.findAll();
        
        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            return fincaDTO;
        }).toList();
    }
    

    public FincaDTO save(FincaDTO fincaDTO) {

        Administrador administrador = administradorRepository.findById(fincaDTO.getIdAdministrador())
                .orElseThrow(() -> new NotFoundException(adminException + fincaDTO.getIdAdministrador() + notFound));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException(departamentoException + fincaDTO.getIdDepartamento() + notFound));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException(municipioException + fincaDTO.getIdMunicipio() + notFound));


        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        fincaDTO.setId(finca.getId());
        return fincaDTO;
    }

    public FincaDTO update(FincaDTO fincaDTO) throws RuntimeException {
        Optional<Finca> fincaOptional = fincaRepository.findById(fincaDTO.getId());
        if (fincaOptional.isEmpty()) {
            throw new NotFoundException(fincaException + fincaDTO.getId() + notFound);
        }

        Administrador administrador = administradorRepository.findById(fincaDTO.getIdAdministrador())
                .orElseThrow(() -> new NotFoundException(adminException + fincaDTO.getIdAdministrador() + notFound));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException(departamentoException + fincaDTO.getIdDepartamento() + notFound));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException(municipioException + fincaDTO.getIdMunicipio() + notFound));

        Finca finca = fincaOptional.get();
        finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        FincaDTO resp = modelMapper.map(finca, FincaDTO.class);
        resp.setIdAdministrador(finca.getAdministrador().getId());
        resp.setIdDepartamento(finca.getDepartamento().getId());
        resp.setIdMunicipio(finca.getMunicipio().getId());
        return resp;
    }

    public void delete(long id) {
        Finca finca = fincaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(fincaException + id + notEncontrado));
        finca.setStatus(1);
        fincaRepository.save(finca);
    }

    public FincaDTO createFinca(FincaDTO fincaDTO, long idAdmin) {
        Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException(adminException + idAdmin + notEncontrado));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException(departamentoException + fincaDTO.getIdDepartamento() + notFound));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException(municipioException + fincaDTO.getIdMunicipio() + notFound));

        
        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);

        return modelMapper.map(finca, FincaDTO.class);
    }

    public List<FincaDTO> getFincasByAdministrador(long idAdmin) {
        Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException(adminException + idAdmin + notEncontrado));
    
        List<Finca> fincas = fincaRepository.findByAdministrador(administrador);
        
        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            return fincaDTO;
        }).toList();
    }

    public List<FincaDTO> getFincasByDepartamento(Long idDepartamento) {
        List<Finca> fincas = fincaRepository.findByDepartamentoId(idDepartamento);

        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            return fincaDTO;
        }).toList();
    }

    public List<FincaDTO> getFincasByNombre(String name) {
        List<Finca> fincas = fincaRepository.findByNombre(name);

        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            return fincaDTO;
        }).toList();
    }

    public List<FincaDTO> getFincasByHabitaciones(int habitaciones) {
        List<Finca> fincas = fincaRepository.findByHabitaciones(habitaciones);

        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            return fincaDTO;
        }).toList();
    }

}
