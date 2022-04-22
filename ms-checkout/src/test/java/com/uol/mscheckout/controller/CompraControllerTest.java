package com.uol.mscheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uol.mscheckout.dto.CarrinhoFormDto;
import com.uol.mscheckout.dto.CompraFormDto;
import com.uol.mscheckout.exceptions.ProdutoNotActiveException;
import com.uol.mscheckout.service.CompraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompraControllerTest {

    private static final String COMPRA_URL = "/v1/purchases";


    @MockBean
    private CompraService compraService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveriaRetornarCreatedAoCriarCompra() throws Exception {

        CompraFormDto compraFormDto = criaCompraFormDto();

        mockMvc.perform(post(COMPRA_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraFormDto)))

                .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarBadRequestAoCriarCompraCoIdDePagamentoNulo() throws Exception{

        CompraFormDto compraFormDto = criaCompraFormDto();
        compraFormDto.setPayment_id(null);

        mockMvc.perform(MockMvcRequestBuilders.post(COMPRA_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraFormDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void aoChamarMetodoGetDeveriaRetornarMetodoNaoPermitido() throws Exception{

        mockMvc.perform(get(COMPRA_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }





    private CompraFormDto criaCompraFormDto() {
        return new CompraFormDto(
                1L,
                1L,
                Collections.singletonList(criaLista())
        );

    }

    private CarrinhoFormDto criaLista(){
        return new CarrinhoFormDto(
                "id",
                1
        );
    }
    


}