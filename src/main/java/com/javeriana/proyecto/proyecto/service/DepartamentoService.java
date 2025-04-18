package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.DepartamentoDTO;
import com.javeriana.proyecto.proyecto.entidades.Departamento;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.DepartamentoRepository;

@Service
public class DepartamentoService {

    DepartamentoRepository departamentoRepository;
    ModelMapper modelMapper;

    @Autowired
    public DepartamentoService(DepartamentoRepository departamentoRepository, ModelMapper modelMapper) {
        this.departamentoRepository = departamentoRepository;
        this.modelMapper = modelMapper;
    }

    public DepartamentoDTO get(long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamento with ID " + id + " not found"));
        return modelMapper.map(departamento, DepartamentoDTO.class);
    }

    public List<DepartamentoDTO> get() {
        List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
        List<DepartamentoDTO> departamentoDTOs = departamentos.stream()
                                                .map(departamento -> modelMapper.map(departamento, DepartamentoDTO.class))
                                                .toList();
        return departamentoDTOs;
    }

    public DepartamentoDTO save(DepartamentoDTO departamentoDTO) {
        Departamento departamento = modelMapper.map(departamentoDTO, Departamento.class);
        departamento = departamentoRepository.save(departamento);
        departamentoDTO.setId(departamento.getId());
        return departamentoDTO;
    }

    public DepartamentoDTO update(DepartamentoDTO departamentoDTO) throws RuntimeException {
        Optional<Departamento> deOptional = departamentoRepository.findById(departamentoDTO.getId());
        if (deOptional.isEmpty()) {
            throw new NotFoundException("Departamento with ID " + departamentoDTO.getId() + " not found");
        }
        Departamento departamento = deOptional.get();
        departamento = modelMapper.map(departamentoDTO, Departamento.class);
        departamento = departamentoRepository.save(departamento);
        return modelMapper.map(departamento, DepartamentoDTO.class);
    }

    public void delete(long id) {
        departamentoRepository.deleteById(id);
    }
}
