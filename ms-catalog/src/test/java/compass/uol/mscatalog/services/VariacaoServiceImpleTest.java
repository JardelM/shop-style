package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.entity.Produto;
import compass.uol.mscatalog.entity.Variacao;
import compass.uol.mscatalog.repository.ProdutoRepository;
import compass.uol.mscatalog.repository.VariacaoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class VariacaoServiceImpleTest {

    @MockBean
    private VariacaoRepository variacaoRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private VariacaoServiceImple service;


    @Test
    void deveriaRetornarCreatedAoCriarUmaVariacao(){

        Produto produto = criaProduto();
        Variacao variacao = criaVariacao();
        VariacaoFormDto variacaoFormDto = criaVariacaoFormDto();
        VariacaoDto variacaoEsperadaDto = criaVariacaoDto();

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(modelMapper.map(variacaoFormDto, Variacao.class)).thenReturn(variacao);
        when(variacaoRepository.save(variacao)).thenReturn(variacao);
        when(modelMapper.map(variacao , VariacaoDto.class)).thenReturn(variacaoEsperadaDto);

        VariacaoDto atual = service.createVariation(variacaoFormDto);
        assertEquals(variacaoEsperadaDto , atual);

    }

    @Test
    void deveriaRetornarUmaVariacaoAtualizada(){
        String id = "id";
        Produto produto = criaProduto();
        Variacao variacao = criaVariacao();
        VariacaoFormDto variacaoFormDto = criaVariacaoFormDto();
        VariacaoDto variacaoEsperadaDto = criaVariacaoDto();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(modelMapper.map(variacaoFormDto , Variacao.class)).thenReturn(variacao);
        when(variacaoRepository.save(variacao)).thenReturn(variacao);
        when(modelMapper.map(variacao , VariacaoDto.class)).thenReturn(variacaoEsperadaDto);

        VariacaoDto atual = service.updateVariation(id , variacaoFormDto);
        assertEquals(variacaoEsperadaDto , atual);

    }

    @Test
    void deveriaDeletarUmaVariacao(){
        String id = "id";
        Variacao variacao = criaVariacao();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        doNothing().when(variacaoRepository).deleteById(id);
        service.deleteVariation(id);
        verify(variacaoRepository , times(1)).deleteById(id);
    }





    private Variacao criaVariacao() {
        return new Variacao(
                "id",
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

    private VariacaoDto criaVariacaoDto() {
        return new VariacaoDto(
                "456",
                "Azul",
                "M",
                new BigDecimal("349.99"),
                15
        );
    }


    private Produto criaProduto(){
        List<Variacao> variations = new ArrayList<>();
        variations.add(new Variacao());

        return new Produto(
                "id",
                "Camisa Oficial Cruzeiro",
                "Pra você Azul de coração",
                true,
                variations
        );
    }

}