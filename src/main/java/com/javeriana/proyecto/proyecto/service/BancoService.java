package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.BancoDTO;
import com.javeriana.proyecto.proyecto.entidades.Banco;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.BancoRepository;

@Service
public class BancoService {

    @Autowired
    BancoRepository BancoRepository;
    @Autowired
    ModelMapper modelMapper;

    private String bancoException = "Banco with ID ";
    private String notFound = " not found";

    public BancoDTO get(long id) {
        Banco banco = BancoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(bancoException + id + notFound));
        return modelMapper.map(banco, BancoDTO.class);
    }

    public List<BancoDTO> get() {
        List<Banco> Bancoes = (List<Banco>) BancoRepository.findAll();
        List<BancoDTO> BancoDTOs = Bancoes.stream()
                                                    .map(Banco -> modelMapper.map(Banco, BancoDTO.class))
                                                    .collect(Collectors.toList());
        return BancoDTOs;
    }

    public BancoDTO save(BancoDTO BancoDTO) {
        Banco Banco = modelMapper.map(BancoDTO, Banco.class);
        Banco = BancoRepository.save(Banco);
        BancoDTO.setId(Banco.getId());
        return BancoDTO;
    }

    public BancoDTO update(BancoDTO BancoDTO) {
        Optional<Banco> BancoOptional = BancoRepository.findById(BancoDTO.getId());
        if (BancoOptional.isEmpty()) {
            throw new NotFoundException(bancoException + BancoDTO.getId() + notFound);
        }
        Banco Banco = BancoOptional.get();
        Banco = modelMapper.map(BancoDTO, Banco.class);
        Banco = BancoRepository.save(Banco);
        return modelMapper.map(Banco, BancoDTO.class);
    }

    public void delete(long id) {
        if (!BancoRepository.existsById(id)) {
            throw new NotFoundException(bancoException + id + notFound);
        }
        BancoRepository.deleteById(id);
    }
}
