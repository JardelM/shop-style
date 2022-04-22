package com.compass.mshistory.controller;

import com.compass.mshistory.exceptions.HistoricoNotFoundException;
import com.compass.mshistory.service.HistoricoService;
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

@SpringBootTest
@AutoConfigureMockMvc
class HistoricoControllerTest {

    private static final String HISTORICO_URL = "/v1/historic/user/";
    private static final String HISTORICO_ID = "/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoricoService historicoService;

    @Test
    void deveriaRetornarOkAoBuscarUmaCompraSalva() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(HISTORICO_URL+HISTORICO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void dadoIdInexistenteDeveRetornarNotFound() throws Exception{

        Long id = 1L;
        Mockito.when(historicoService.findUserHistoric(id)).thenThrow(HistoricoNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(HISTORICO_URL+HISTORICO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}