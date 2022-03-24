package compass.uol.mscatalog.mscatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.mscatalog.services.VariacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VariacaoControllerTest {

    private static final String VARIACAO_URL = "/v1/variations";
    private static final String VARIACAO_ID = "/id";

    @MockBean
    private VariacaoService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveriaRetornarCreatedAoCriarUmaVariacao() throws Exception{

        VariacaoFormDto variacaoFormDto = criaVariacaoFormDto();
        VariacaoDto variacaoDto = criaVariacaoDto();

        when(this.service.createVariation(variacaoFormDto)).thenReturn(variacaoDto);

        mockMvc.perform(post(VARIACAO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variacaoFormDto)))
                .andExpect(status().isCreated());

    }

    @Test
    void deveriaRetornarOkAoAtualizarUmaVariacao() throws Exception{
        String id = "id";
        VariacaoDto variacaoDto = criaVariacaoDto();
        VariacaoFormDto variacaoFormDto = criaVariacaoFormDto();

        when(this.service.updateVariation(id , variacaoFormDto)).thenReturn(variacaoDto);

        mockMvc.perform(put(VARIACAO_URL+VARIACAO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variacaoFormDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarOkAoDeletarUmaVariacao() throws Exception{

        mockMvc.perform(delete(VARIACAO_URL+VARIACAO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deveriaRetornarOkAoBuscarPorIdValido() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(VARIACAO_URL+VARIACAO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }





    private VariacaoDto criaVariacaoDto() {
        return new VariacaoDto(
                "456",
                "Azul",
                "M",
                new BigDecimal("349.99"),
                15
        );
    }

    private VariacaoFormDto criaVariacaoFormDto() {
        return new VariacaoFormDto(
                "Azul",
                "M",
                new BigDecimal("349.99"),
                15,
                "id"
        );
    }

}