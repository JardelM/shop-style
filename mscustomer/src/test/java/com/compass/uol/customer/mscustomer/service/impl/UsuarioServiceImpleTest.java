package com.compass.uol.customer.mscustomer.service.impl;

import com.compass.uol.customer.mscustomer.dto.UsuarioDto;
import com.compass.uol.customer.mscustomer.dto.UsuarioFormDto;
import com.compass.uol.customer.mscustomer.entity.Usuario;
import com.compass.uol.customer.mscustomer.enums.Sexo;
import com.compass.uol.customer.mscustomer.exceptions.UserAlreadyExistsException;
import com.compass.uol.customer.mscustomer.repository.UsuarioRepository;
import com.compass.uol.customer.mscustomer.services.UsuarioServiceImple;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceImpleTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean //vc determina o retorno
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioServiceImple service;

    @Test
    void dadoUmIdDeveRetornarUmUsuario(){
        Usuario usuario = criaUsuario();
        UsuarioDto usuarioEsperadoDto = criaUsuarioDto();

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(modelMapper.map(usuario, UsuarioDto.class)).thenReturn(usuarioEsperadoDto);

        UsuarioDto usuarioAtualDto = this.service.getUser(usuario.getId());
        assertEquals(usuarioEsperadoDto, usuarioAtualDto);
    }


    @Test
    void deveRetornarCreatedAoCriarUmUsuario(){
        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        Usuario usuario = criaUsuario();
        UsuarioDto usuarioEsperadoDto = criaUsuarioDto();

        when(usuarioRepository.findByEmail(usuarioFormDto.getEmail())).thenReturn(Optional.empty()); //empty nao te da a exceçao
        when (modelMapper.map(usuarioFormDto, Usuario.class)).thenReturn(usuario);
        when (usuarioRepository.save(usuario)).thenReturn(usuario);
        when(modelMapper.map(usuario,UsuarioDto.class)).thenReturn(usuarioEsperadoDto);

        UsuarioDto usuarioAtualDto = service.createUser(usuarioFormDto);
        assertEquals (usuarioEsperadoDto, usuarioAtualDto);
    }


    @Test
    void dadoEmailJaExistenteAoCriarDeveRetornarException(){
        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        Usuario usuario = criaUsuario();

        when(usuarioRepository.findByEmail(usuarioFormDto.getEmail())).thenReturn(Optional.of(usuario));

        assertThrows(UserAlreadyExistsException.class, () -> service.createUser(usuarioFormDto));
    }

    @Test
    void deveRetornarExcecaoAoAtualizarUsuarioComEmailJaCadastrado(){
        Usuario usuario = criaUsuario();
        Usuario usuarioEncontrado = criaUsuario();
        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        usuarioEncontrado.setId(2L);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail(usuarioFormDto.getEmail())).thenReturn(Optional.of(usuarioEncontrado));

        assertThrows(UserAlreadyExistsException.class, () -> service.updateUser(1L, usuarioFormDto));
    }



    private UsuarioFormDto criaUsuarioFormDto(){
        return new UsuarioFormDto(
                "Joao",
                "Silva",
                Sexo.MASCULINO,
                "702.089.046-62",
                LocalDate.now(),
                "joao@email.com",
                "12345678",
                true);
    }

    private Usuario criaUsuario(){
        return new Usuario(
                1L,
                "Joao",
                "Silva",
                Sexo.MASCULINO,
                "702.089.046-62",
                LocalDate.now(),
                "joao@email.com",
                "12345678",
                true);

    }

    private UsuarioDto criaUsuarioDto(){
        return new UsuarioDto(
                1L,
                "Joao",
                "Silva",
                Sexo.MASCULINO,
                "702.089.046-62",
                LocalDate.now(),
                "joao@email.com",
                "12345678",
                true);

    }
}
