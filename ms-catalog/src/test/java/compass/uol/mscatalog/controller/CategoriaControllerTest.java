package compass.uol.mscatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import compass.uol.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.services.CategoriaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    private static final String CATEGORIA_URL = "/v1/categories";
    private static final String CATEGORIA_ID = "/id";
    private static final String PRODUTO_URL = "/products";

    @MockBean
    private CategoriaService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveriaRetornarOkAoRecuperarTodasCategorias() throws Exception{
        CategoriaDto categoriaDto = criaCategoriaDto();

        when(this.service.findAll()).thenReturn(Collections.singletonList(categoriaDto));

        mockMvc.perform(get(CATEGORIA_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarCreatedAoCadastrarNovaCategoria() throws Exception{
        CategoriaDto categoriaDto = criaCategoriaDto();
        CategoriaFormDto categoriaFormDto = criaCategoriaFormDto();

        when(this.service.createCategory(categoriaFormDto)).thenReturn(categoriaDto);

        mockMvc.perform(post(CATEGORIA_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarOkAoTentarRecuperarProdutosPelaCategoria() throws Exception{
        String id = "id";
        ProdutoDto produtoDto = criaProdutoDto();

        when(this.service.findProductsFromCategory(id)).thenReturn(Collections.singletonList(produtoDto));

        mockMvc.perform(get(CATEGORIA_URL+CATEGORIA_ID+PRODUTO_URL)
                .contentType(MediaType.APPLICATION_JSON))
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

    private VariacaoDto criaVariacaoDto() {
        return new VariacaoDto(
                "456",
                "Azul",
                "M",
                new BigDecimal("349.99"),
                15
        );
    }

    private CategoriaDto criaCategoriaDto(){
        return new CategoriaDto(
                "id",
                "Camisas de treino",
                true
        );
    }

    private CategoriaFormDto criaCategoriaFormDto(){
        return new CategoriaFormDto(
                "Camisas de treino",
                true
        );
    }





}