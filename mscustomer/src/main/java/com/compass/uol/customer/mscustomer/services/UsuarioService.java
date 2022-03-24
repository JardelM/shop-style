package com.compass.uol.customer.mscustomer.services;


import com.compass.uol.customer.mscustomer.dto.UsuarioDto;
import com.compass.uol.customer.mscustomer.dto.UsuarioFormDto;

public interface UsuarioService {

    UsuarioDto getUser(Long id);

    UsuarioDto updateUser(Long id, UsuarioFormDto body);

    UsuarioDto createUser(UsuarioFormDto usuarioFormDto);
}
