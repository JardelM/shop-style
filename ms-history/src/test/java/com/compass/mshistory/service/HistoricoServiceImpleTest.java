package com.compass.mshistory.service;

import com.compass.mshistory.client.CatalogClient;
import com.compass.mshistory.client.CheckoutClient;
import com.compass.mshistory.client.CustomerClient;
import com.compass.mshistory.dto.*;
import com.compass.mshistory.entity.Compra;
import com.compass.mshistory.entity.Historico;
import com.compass.mshistory.entity.Variacao;
import com.compass.mshistory.repository.CompraRepository;
import com.compass.mshistory.repository.HistoricoRepository;
import com.compass.mshistory.repository.VariacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HistoricoServiceImpleTest {

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private CheckoutClient checkoutClient;

    @Mock
    private CatalogClient catalogClient;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HistoricoServiceImple historicoService;

    @Test
    void deveriaRetornarUmHistoricoComSucesso(){

        Long usuarioId = 1L;
        Historico historico = criaHistorico();
        UsuarioDto usuarioDto = criaUsuarioDto();
        PagamentoDto pagamentoDto = criaPagamentoDto();
        Compra compra = criaCompra();
        Variacao variacao = criaVariacao();
        ProdutoDto produtoDto = criaProdutoDto();
        VariacaoClientDto variacaoClientDto = criaVariacaoClient();
        HistoricoDto historicoDtoEsperado = criaHistoricoDto();

        Mockito.when(historicoRepository.findByUserId(usuarioId)).thenReturn(Optional.of(historico));
        Mockito.when(customerClient.getUser(historico.getUserId())).thenReturn(usuarioDto);
        Mockito.when(checkoutClient.findById(compra.getPaymentId())).thenReturn(pagamentoDto);
        Mockito.when(catalogClient.getVariation(variacao.getVariant_id())).thenReturn(variacaoClientDto);
        Mockito.when(catalogClient.getProduct(variacaoClientDto.getProduct_id())).thenReturn(produtoDto);

        HistoricoDto historicoAtual = historicoService.findUserHistoric(usuarioId);
        Assertions.assertEquals(historicoDtoEsperado , historicoAtual);

    }


    @Test
    void deveriaInserirUmaCompraComSucesso(){

        Compra compra = criaCompra();
        Historico historico = criaHistorico();
        CompraMessageDto compraMessageDto = criaCompraMessageDto();

        Mockito.when(historicoRepository.findByUserId(compraMessageDto.getUserId())).thenReturn(Optional.of(historico));
        Mockito.when(modelMapper.map(compraMessageDto, Compra.class)).thenReturn(compra);
        Mockito.when(compraRepository.save(compra)).thenReturn(compra);

        historicoService.insereNovaCompra(compraMessageDto);

        verify(historicoRepository, times(1)).save(historico);


    }

    private HistoricoDto criaHistoricoDto() {
        List<CompraDto> compraDtos = new ArrayList<>();
        compraDtos.add(criaCompraDto());
        return new HistoricoDto(criaUsuarioDto() , compraDtos);
    }

    private CompraDto criaCompraDto(){
        List<ProdutoDto> produtos = new ArrayList<>();
        produtos.add(criaProdutoDto());
        return new CompraDto(criaPagamentoDto() , produtos, BigDecimal.ZERO, LocalDate.now());
    }

    private VariacaoClientDto criaVariacaoClient() {
        return new VariacaoClientDto("azul",
                "M", BigDecimal.ZERO,
                5,
                "id");
    }

    private ProdutoDto criaProdutoDto() {
        return new ProdutoDto("Camisa Cruzeiro Oficial",
                "Camisa do Cabuloso",
                "azul",
                "M",
                BigDecimal.ZERO,
                5);
    }

    private PagamentoDto criaPagamentoDto() {
        return new PagamentoDto("PIX",
                BigDecimal.ZERO,
                true);
    }

    private UsuarioDto criaUsuarioDto() {
        return new UsuarioDto("Jessica",
                "Souza",
                "FEMININO",
                "123.456.789-10",
                LocalDate.now(),
                "jessica@gmail.com");
    }

    private CompraMessageDto criaCompraMessageDto() {
        return new CompraMessageDto(1L,
                1L,
                BigDecimal.ZERO,
                LocalDate.now(),
                new ArrayList<>());
    }

    private Historico criaHistorico() {
        List<Compra> compras = new ArrayList<>();
        compras.add(criaCompra());
        return new Historico("id", 1L, compras);
    }

    private Variacao criaVariacao(){
        return new Variacao("id", "variant_id", 5);
    }

    private Compra criaCompra() {
        List<Variacao> variacoes = new ArrayList<>();
        variacoes.add(criaVariacao());
        return new Compra("id" , 1L, BigDecimal.ZERO, LocalDate.now() , variacoes);
    }

}