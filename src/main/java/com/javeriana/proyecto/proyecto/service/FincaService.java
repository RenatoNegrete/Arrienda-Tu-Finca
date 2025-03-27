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

    @Autowired
    FincaRepository fincaRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    ModelMapper modelMapper;

    public FincaDTO get(long id) {
        Finca finca = fincaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Finca with ID " + id + " not found"));
        
        FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
        fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
        fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
        fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
        fincaDTO.setIdFoto(finca.getFoto() != null ? finca.getFoto().getId() : null);

        return fincaDTO;
    }

    public List<FincaDTO> get() {
        List<Finca> fincas = (List<Finca>) fincaRepository.findAll();
        
        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            fincaDTO.setIdFoto(finca.getFoto() != null ? finca.getFoto().getId() : null);
            return fincaDTO;
        }).collect(Collectors.toList());
    }
    

    public FincaDTO save(FincaDTO fincaDTO) {

        Administrador administrador = administradorRepository.findById(fincaDTO.getIdAdministrador())
                .orElseThrow(() -> new NotFoundException("Administrador with ID " + fincaDTO.getIdAdministrador() + " not found"));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException("Departamento with ID " + fincaDTO.getIdDepartamento() + " not found"));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException("Municipio with ID " + fincaDTO.getIdMunicipio() + " not found"));

        Foto foto = null;
        if (fincaDTO.getIdFoto() != null) {
            foto = fotoRepository.findById(fincaDTO.getIdFoto())
                    .orElseThrow(() -> new NotFoundException("Foto with ID " + fincaDTO.getIdFoto() + " not found"));
        }

        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setFoto(foto);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        fincaDTO.setId(finca.getId());
        return fincaDTO;
    }

    public FincaDTO update(FincaDTO fincaDTO) throws RuntimeException {
        Optional<Finca> fincaOptional = fincaRepository.findById(fincaDTO.getId());
        if (fincaOptional.isEmpty()) {
            throw new NotFoundException("Finca with ID " + fincaDTO.getId() + " not found");
        }

        Administrador administrador = administradorRepository.findById(fincaDTO.getIdAdministrador())
                .orElseThrow(() -> new NotFoundException("Administrador with ID " + fincaDTO.getIdAdministrador() + " not found"));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException("Departamento with ID " + fincaDTO.getIdDepartamento() + " not found"));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException("Municipio with ID " + fincaDTO.getIdMunicipio() + " not found"));

        Foto foto = null;
        if (fincaDTO.getIdFoto() != null) {
            foto = fotoRepository.findById(fincaDTO.getIdFoto())
                    .orElseThrow(() -> new NotFoundException("Foto with ID " + fincaDTO.getIdFoto() + " not found"));
        }

        Finca finca = fincaOptional.get();
        finca = modelMapper.map(fincaDTO, Finca.class);
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setFoto(foto);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);
        return modelMapper.map(finca, FincaDTO.class);
    }

    public void delete(long id) {
        Finca finca = fincaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Finca with ID " + id + " no encontrado"));
        finca.setStatus(1);
        fincaRepository.save(finca);
    }

    public FincaDTO createFinca(FincaDTO fincaDTO, long idAdmin) {
        Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Administrdor with ID " + idAdmin + " no encontrado"));

        Departamento departamento = departamentoRepository.findById(fincaDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException("Departamento with ID " + fincaDTO.getIdDepartamento() + " not found"));

        Municipio municipio = municipioRepository.findById(fincaDTO.getIdMunicipio())
                .orElseThrow(() -> new NotFoundException("Municipio with ID " + fincaDTO.getIdMunicipio() + " not found"));

        Foto foto = null;
        if (fincaDTO.getIdFoto() != null) {
            foto = fotoRepository.findById(fincaDTO.getIdFoto())
                    .orElseThrow(() -> new NotFoundException("Foto with ID " + fincaDTO.getIdFoto() + " not found"));
        }
        
        Finca finca = modelMapper.map(fincaDTO, Finca.class);
        
        finca.setAdministrador(administrador);
        finca.setDepartamento(departamento);
        finca.setMunicipio(municipio);
        finca.setFoto(foto);
        finca.setStatus(0);
        finca = fincaRepository.save(finca);

        return modelMapper.map(finca, FincaDTO.class);
    }

    public List<FincaDTO> getFincasByAdministrador(long idAdmin) {
        Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Administrdor with ID " + idAdmin + " no encontrado"));
    
        List<Finca> fincas = fincaRepository.findByAdministrador(administrador);
        
        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            fincaDTO.setIdFoto(finca.getFoto() != null ? finca.getFoto().getId() : null);
            return fincaDTO;
        }).collect(Collectors.toList());
    }

    public List<FincaDTO> getFincasByDepartamento(Long idDepartamento) {
        List<Finca> fincas = fincaRepository.findByDepartamentoId(idDepartamento);

        return fincas.stream().map(finca -> {
            FincaDTO fincaDTO = modelMapper.map(finca, FincaDTO.class);
            fincaDTO.setIdAdministrador(finca.getAdministrador() != null ? finca.getAdministrador().getId() : null);
            fincaDTO.setIdDepartamento(finca.getDepartamento() != null ? finca.getDepartamento().getId() : null);
            fincaDTO.setIdMunicipio(finca.getMunicipio() != null ? finca.getMunicipio().getId() : null);
            fincaDTO.setIdFoto(finca.getFoto() != null ? finca.getFoto().getId() : null);
            return fincaDTO;
        }).collect(Collectors.toList());
    }

}
