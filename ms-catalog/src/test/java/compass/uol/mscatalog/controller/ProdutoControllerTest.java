package compass.uol.mscatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.dto.ProdutoFormDto;
import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.services.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

    private static final String PRODUTO_URL = "/v1/products";
    private static final String PRODUTO_ID = "/id";

    @MockBean
    private ProdutoService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveriaRetornarCreatedAoCadastrarUmProduto() throws Exception{

        ProdutoDto produtoDto = criaProdutoDto();
        ProdutoFormDto produtoFormDto = criaProdutoForm();

        when(this.service.createProduct(produtoFormDto)).thenReturn(produtoDto);

        mockMvc.perform(post(PRODUTO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoFormDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarOkAoRecuperarTodosOsProdutos() throws Exception{

        ProdutoDto produtoDto = criaProdutoDto();

        when(this.service.findAll()).thenReturn(Collections.singletonList(produtoDto));

        mockMvc.perform(get(PRODUTO_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarOkAoBuscarIdValidoDeProduto() throws Exception{
        String id = "id";
        ProdutoDto produtoDto = criaProdutoDto();

        when(this.service.getProduct(id)).thenReturn(produtoDto);

        mockMvc.perform(get(PRODUTO_URL+PRODUTO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarOkAoDeletarUmIdValido() throws Exception{

        mockMvc.perform(delete(PRODUTO_URL+PRODUTO_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarOkAoAtualizarUmProduto() throws Exception{

        ProdutoFormDto produtoFormDto = criaProdutoForm();
        ProdutoDto produtoDto = criaProdutoDto();
        String id = "id";

        when(this.service.updateProduct(id , produtoFormDto)).thenReturn(produtoDto);

        mockMvc.perform(put(PRODUTO_URL+PRODUTO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoFormDto)))
                .andExpect(status().isOk());
    }




    private ProdutoDto criaProdutoDto(){

        return new ProdutoDto(
                "id",
                "Camisa Oficial Cruzeiro",
                "Pra você Azul de coração",
                true,
                Collections.singletonList(criaVariacaoDto())
        );
    }

    private ProdutoFormDto criaProdutoForm(){

        return new ProdutoFormDto (
                "Camisa Oficial Cruzeiro",
                "Pra você Azul de coração",
                true,
                Collections.singletonList("id")
        );
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

}