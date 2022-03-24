package com.compass.uol.customer.mscustomer.controller;

import com.compass.uol.customer.mscustomer.dto.UsuarioDto;
import com.compass.uol.customer.mscustomer.dto.UsuarioFormDto;
import com.compass.uol.customer.mscustomer.enums.Sexo;
import com.compass.uol.customer.mscustomer.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    private static String user_url = "/v1/users";
    private static String user_id = "/1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void deveriaRetornarOkAoBuscarIdValido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(user_url+user_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void deveriaRetornarCreated() throws Exception{

        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        UsuarioDto usuarioDto = criaUsuarioDto();
        Mockito.when(usuarioService.createUser(usuarioFormDto)).thenReturn(usuarioDto);
        mockMvc.perform(MockMvcRequestBuilders.post(user_url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioFormDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void deveriaRetornaremail() throws Exception{

        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        UsuarioDto usuarioDto = criaUsuarioDto();
        Mockito.when(usuarioService.createUser(usuarioFormDto)).thenReturn(usuarioDto);
        mockMvc.perform(MockMvcRequestBuilders.post(user_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioFormDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(usuarioDto.getEmail())));
    }

    @Test
    void deveriaRetornarOkAoAtualizarUmUsuario() throws Exception {

        UsuarioFormDto usuarioFormDto = criaUsuarioFormDto();
        UsuarioDto usuarioDto = criaUsuarioDto();
        Mockito.when(usuarioService.createUser(usuarioFormDto)).thenReturn(usuarioDto);
        mockMvc.perform(MockMvcRequestBuilders.put(user_url+user_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioFormDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    private UsuarioFormDto criaUsuarioFormDto() {
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

    private UsuarioDto criaUsuarioDto() {
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