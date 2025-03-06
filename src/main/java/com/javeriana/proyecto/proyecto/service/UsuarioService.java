package com.javeriana.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.proyecto.proyecto.dto.UsuarioDTO;
import com.javeriana.proyecto.proyecto.entidades.Usuario;
import com.javeriana.proyecto.proyecto.repositorios.UsuarioRepository;

@Service

public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    public UsuarioDTO get(long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        UsuarioDTO usuarioDTO = null;
        if (usuarioOptional != null) {
            usuarioDTO = modelMapper.map(usuarioOptional.get(), UsuarioDTO.class);
        }
        return usuarioDTO;
    }

    public List<UsuarioDTO> get() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOs = usuarios.stream()
                                                    .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                                                    .collect(Collectors.toList());
        return usuarioDTOs;
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setStatus(0);
        usuario = usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());
        return usuarioDTO;
    }

    public UsuarioDTO update(UsuarioDTO usuarioDTO) throws RuntimeException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioDTO.getId());
        if (usuarioOptional == null) {
            throw new RuntimeException("Finca not found");
        }
        Usuario usuario = usuarioOptional.get();
        usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setStatus(0);
        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public void delete(long id) {
        usuarioRepository.deleteById(id);
    }

}
