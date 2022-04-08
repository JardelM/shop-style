package com.uol.mscheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uol.mscheckout.dto.PagamentoDto;
import com.uol.mscheckout.dto.PagamentoFormDto;
import com.uol.mscheckout.entity.TipoPagamento;
import com.uol.mscheckout.service.PagamentoService;
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

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerTest {

    private static final String PAGAMENTO_URL = "/v1/payments";
    private static final String PAGAMENTO_ID = "/1";

    @MockBean
    private PagamentoService pagamentoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveriaRetornarOkAoRecuperarTodosPagamentos() throws Exception{
        PagamentoDto pagamentoDto = criaPagamentoDto();

        when(this.pagamentoService.getAll()).thenReturn(Collections.singletonList(pagamentoDto));

        mockMvc.perform(get(PAGAMENTO_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarCreatedAoCriarUmPagamento() throws Exception{
        PagamentoDto pagamentoDto = criaPagamentoDto();
        PagamentoFormDto pagamentoFormDto = criaPagamentoFormDto();

        when(pagamentoService.createPayment(pagamentoFormDto)).thenReturn(pagamentoDto);

        mockMvc.perform(post(PAGAMENTO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagamentoFormDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarOkAoAtualizarUmPagamento() throws Exception {
        PagamentoDto pagamentoDto = criaPagamentoDto();
        PagamentoFormDto pagamentoFormDto = criaPagamentoFormDto();

        Mockito.when(pagamentoService.createPayment(pagamentoFormDto)).thenReturn(pagamentoDto);

        mockMvc.perform(MockMvcRequestBuilders.put(PAGAMENTO_URL+PAGAMENTO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagamentoFormDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }



    @Test
    void deveriaRetornarOkAoBuscarPorIdValido() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(PAGAMENTO_URL+PAGAMENTO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveriaRetornarOkAoDeletarUmPagamento() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete(PAGAMENTO_URL+PAGAMENTO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }





    private PagamentoFormDto criaPagamentoFormDto() {
        return new PagamentoFormDto(
                TipoPagamento.PIX,
                new BigDecimal(5),
                true
        );
    }


    private PagamentoDto criaPagamentoDto() {
        return new PagamentoDto(
                1L,
                TipoPagamento.PIX,
                new BigDecimal(5),
                true
        );
    }


}