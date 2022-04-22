package com.uol.mscheckout.service;

import com.uol.mscheckout.client.CatalogClient;
import com.uol.mscheckout.client.CustomerClient;
import com.uol.mscheckout.dto.*;
import com.uol.mscheckout.entity.Pagamento;
import com.uol.mscheckout.entity.TipoPagamento;
import com.uol.mscheckout.exceptions.PagamentoNotActiveException;
import com.uol.mscheckout.exceptions.ProdutoNotActiveException;
import com.uol.mscheckout.exceptions.UserNotActiveException;
import com.uol.mscheckout.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CompraServiceImpleTest {


    @Mock
    private CustomerClient customerClient;

    @Mock
    private CatalogClient catalogClient;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private CompraServiceImple compraService;


    @Test
    void deveriaRetornarExcecaoAoCriarCompraPorUsuarioInativo(){

        CompraFormDto compraFormDto = criaCompraFormDto();
        UsuarioAtivoDto usuarioAtivoDto = criaUsuarioAtivo();
        usuarioAtivoDto.setActive(false);


        Mockito.when(customerClient.findById(compraFormDto.getUser_id())).thenReturn(usuarioAtivoDto);
        UserNotActiveException usuarioAtual = assertThrows(UserNotActiveException.class, ()-> compraService.createPurchase(compraFormDto));

        assertEquals("Usuario de id 1 não está ativo" , usuarioAtual.getMessage());


    }

    @Test
    void deveriaRetornarExcecaoAoCriarCompraPorPagamentoInativo(){
        CompraFormDto compraFormDto = criaCompraFormDto();
        UsuarioAtivoDto usuarioAtivoDto = criaUsuarioAtivo();
        Pagamento pagamento = criaPagamento();
        pagamento.setStatus(false);

        Mockito.when(customerClient.findById(compraFormDto.getUser_id())).thenReturn(usuarioAtivoDto);
        Mockito.when(pagamentoRepository.findById(compraFormDto.getPayment_id())).thenReturn(Optional.of(pagamento));

        PagamentoNotActiveException pagamentoAtual = assertThrows(PagamentoNotActiveException.class, ()-> compraService.createPurchase(compraFormDto));

        assertEquals("Pagamento de id 1 inativo", pagamentoAtual.getMessage());

    }

    @Test
    void deveriaRetornarExcecaoAoCriarCompraPorProdutoInativo(){

        CompraFormDto compraFormDto = criaCompraFormDto();
        UsuarioAtivoDto usuarioAtivoDto = criaUsuarioAtivo();
        Pagamento pagamento = criaPagamento();
        VariacaoProdutoDto variacaoProdutoDto = criaVariacaoProdutoDto();
        ProdutoAtivoDto produtoAtivoDto = criaProdutoAtivoDto();
        produtoAtivoDto.setActive(false);
        CarrinhoFormDto carrinhoFormDto = criaCarrinhoFormDto();

        Mockito.when(customerClient.findById(compraFormDto.getUser_id())).thenReturn(usuarioAtivoDto);
        Mockito.when(pagamentoRepository.findById(compraFormDto.getPayment_id())).thenReturn(Optional.of(pagamento));
        Mockito.when(catalogClient.getById(carrinhoFormDto.getVariant_id())).thenReturn(variacaoProdutoDto);
        Mockito.when(catalogClient.findById(variacaoProdutoDto.getProduct_id())).thenReturn(produtoAtivoDto);

        ProdutoNotActiveException produtoAtual = assertThrows(ProdutoNotActiveException.class, ()-> compraService.createPurchase(compraFormDto));

        assertEquals("Produto de id 1 não ativo" , produtoAtual.getMessage());



    }

    private CarrinhoFormDto criaCarrinhoFormDto() {
        return new CarrinhoFormDto("id", 1);
    }

    private ProdutoAtivoDto criaProdutoAtivoDto() {
        return new ProdutoAtivoDto("1", true);
    }

    private VariacaoProdutoDto criaVariacaoProdutoDto() {
        return new VariacaoProdutoDto("id", BigDecimal.ZERO, 5, "idProduto");
    }


    private UsuarioAtivoDto criaUsuarioAtivo() {
        return new UsuarioAtivoDto(true);
    }

    private Pagamento criaPagamento(){
        return new Pagamento(1L , TipoPagamento.PIX, BigDecimal.ZERO , true);
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