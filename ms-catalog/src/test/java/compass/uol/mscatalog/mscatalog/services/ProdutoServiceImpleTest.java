package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoFormDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.entity.Categoria;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.exceptions.CategoryNotActiveException;
import compass.uol.mscatalog.mscatalog.repository.CategoriaRepository;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProdutoServiceImpleTest {

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoServiceImple service;

    @Test
    void deveriaRetornarExcecaoAoTentarVincularProdutoACategoriaInativa(){

        String id = "id";
        ProdutoFormDto produtoFormDto = criaProdutoForm();
        Produto produto = criaProduto();
        Categoria categoria = criaCategoria();
        categoria.setActive(false);

        when(modelMapper.map(produtoFormDto , Produto.class)).thenReturn(produto);
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria)); //se comentar essa linha, dá erro de Category not found

        assertThrows(CategoryNotActiveException.class, ()-> service.createProduct(produtoFormDto));


    }

    @Test
    void deveriaRetornarUmaListaDeProdutos(){

        Produto produto = criaProduto();
        ProdutoDto produtoDto = criaProdutoDto();
        List<ProdutoDto> listaEsperada = Collections.singletonList(produtoDto);

        when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produto));
        when(modelMapper.map(produto , ProdutoDto.class)).thenReturn(produtoDto);
        List<ProdutoDto> atual = service.findAll();

        assertEquals(listaEsperada , atual);

    }

    @Test
    void deveriaRetornarUmProdutoPeloId(){
        String id = "id";
        Produto produto = criaProduto();
        ProdutoDto produtoDtoEsperado = criaProdutoDto();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(modelMapper.map(produto , ProdutoDto.class)).thenReturn(produtoDtoEsperado);

        ProdutoDto atual = service.getProduct(id);
        assertEquals(atual , produtoDtoEsperado);
    }

    @Test
    void deveriaDeletarUmProdutoComSucesso(){
        String id = "id";
        Produto produto = criaProduto();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).deleteById(id);
        service.deleteProduct(id);
        verify(produtoRepository , times(1)).deleteById(id);

    }

    @Test
    void deveriaAtualizarUmProdutoComSucesso(){
        String id = "id";
        ProdutoFormDto produtoFormDto = criaProdutoForm();
        Produto produto = criaProduto();
        Categoria categoria = criaCategoria();
        ProdutoDto produtoDtoEsperado = criaProdutoDto();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(modelMapper.map(produtoFormDto , Produto.class)).thenReturn(produto);
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(modelMapper.map(produto , ProdutoDto.class)).thenReturn(produtoDtoEsperado);

        ProdutoDto atual = service.updateProduct(id, produtoFormDto);
        assertEquals(atual, produtoDtoEsperado);

    }

    @Test
    void deveriaCriarUmProdutoComSucesso(){
        String id = "id";
        ProdutoFormDto produtoFormDto = criaProdutoForm();
        Produto produto = criaProduto();
        Categoria categoria = criaCategoria();
        ProdutoDto produtoDtoEsperado = criaProdutoDto();
        List<Categoria> todasCategorias = new ArrayList<>();

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(modelMapper.map(produtoFormDto , Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaRepository.findAll()).thenReturn(todasCategorias);
        when(modelMapper.map(produto, ProdutoDto.class)).thenReturn(produtoDtoEsperado);

        ProdutoDto atual = service.createProduct(produtoFormDto);
        assertEquals(produtoDtoEsperado , atual);


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

    private ProdutoFormDto criaProdutoForm(){

        return new ProdutoFormDto (
                "Camisa Oficial Cruzeiro",
                "Pra você Azul de coração",
                true,
                Collections.singletonList("id")
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

    private Categoria criaCategoria(){
        return new Categoria(
                "id",
                "Camisas de futebol",
                true,
                new ArrayList<>()
        );
    }

}