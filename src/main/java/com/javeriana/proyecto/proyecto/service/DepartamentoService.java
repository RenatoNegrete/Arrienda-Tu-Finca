package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.DepartamentoDTO;
import com.javeriana.proyecto.proyecto.entidades.Departamento;
import com.javeriana.proyecto.proyecto.repositorios.DepartamentoRepository;

@Service
public class DepartamentoService {
     @Autowired
    DepartamentoRepository departamentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public DepartamentoDTO get(long id) {
        Optional<Departamento> fincaOptional = departamentoRepository.findById(id);
        DepartamentoDTO departamentoDTO = null;
        if (fincaOptional != null) {
            departamentoDTO = modelMapper.map(fincaOptional.get(), DepartamentoDTO.class);
        }
        return departamentoDTO;
    }

    public List<DepartamentoDTO> get() {
        List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
        List<DepartamentoDTO> departamentoDTOs = departamentos.stream()
                                                .map(departamento -> modelMapper.map(departamento, DepartamentoDTO.class))
                                                .collect(Collectors.toList());
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
            throw new RuntimeException("Finca not found");
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
