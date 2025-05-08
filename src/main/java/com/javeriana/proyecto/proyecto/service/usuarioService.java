package com.javeriana.proyecto.proyecto.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.javeriana.proyecto.proyecto.dto.UsuarioDTO;

@Service
public class usuarioService {


    // @Autowired
    // UsuarioRepository usuarioRepository;
    @Autowired
	ModelMapper modelMapper;
    

    public UsuarioDTO autorizacion( Authentication authentication ) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("-----------------------");
        System.out.println(  authentication.getName() );
        UsuarioDTO usuario = objectMapper.readValue(authentication.getName(), UsuarioDTO.class);
        System.out.println("-----------------------"); 
        return usuario;
    }
}