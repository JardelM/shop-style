package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.entity.Categoria;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.repository.CategoriaRepository;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaServiceImpleTest {

    @Autowired
    private CategoriaServiceImple service;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void deveriaRetornarCreatedAoCriarUmaCategoria(){
        CategoriaFormDto categoriaFormDto = criaCategoriaFormDto();
        CategoriaDto categoriaDtoEsperada = criaCategoriaDto();
        Categoria categoria = criaCategoria();

        when(modelMapper.map(categoriaFormDto , Categoria.class)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(modelMapper.map(categoria , CategoriaDto.class)).thenReturn(categoriaDtoEsperada);

        CategoriaDto atual = service.createCategory(categoriaFormDto);
        assertEquals(categoriaDtoEsperada , atual);
    }

    @Test
    void deveriaDeletarUmaCategoria(){
        String id = "id";
        Categoria categoria = criaCategoria();

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).deleteById(id);

        service.delete(id);
        verify(categoriaRepository , times(1)).deleteById(id);
    }

    @Test
    void deveriaRetornarUmaListaDeCategorias(){
        Categoria categoria = criaCategoria();
        CategoriaDto categoriaDtoEsperada = criaCategoriaDto();

        when(categoriaRepository.findAll()).thenReturn(Collections.singletonList(categoria));
        when(modelMapper.map(categoria , CategoriaDto.class)).thenReturn(categoriaDtoEsperada);

        List<CategoriaDto> atual = service.findAll();
        assertEquals(Collections.singletonList(categoriaDtoEsperada) , atual);
    }

    @Test
    void deveriaRetornarTodosProdutosDeDeterminadaCategoria(){
        String id = "id";
        Categoria categoria = criaCategoria();
        Produto produto = criaProduto();
        ProdutoDto produtoDtoEsperado = criaProdutoDto();

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(modelMapper.map(produto , ProdutoDto.class)).thenReturn(produtoDtoEsperado);

        List<ProdutoDto> atual = service.findAllProductsByCategory(id);
        assertEquals(Collections.singletonList(produtoDtoEsperado) , atual);
    }


    private Categoria criaCategoria(){
        List<Produto> produtos = new ArrayList<>();
        produtos.add(criaProduto());
        return new Categoria(
                "id",
                "Camisas de treino",
                true,
                produtos
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



    private Variacao criaVariacao() {
        return new Variacao(
                "id",
                "Azul",
                "M",
                new BigDecimal("349.99"),
                15
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

    private ProdutoDto criaProdutoDto(){

        return new ProdutoDto(
                "id",
                "Camisa Oficial Cruzeiro",
                "Pra você Azul de coração",
                true,
                Collections.singletonList(criaVariacaoDto())
        );
    }



}