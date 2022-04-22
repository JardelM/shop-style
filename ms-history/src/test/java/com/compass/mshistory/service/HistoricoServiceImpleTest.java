package com.compass.mshistory.service;

import com.compass.mshistory.client.CatalogClient;
import com.compass.mshistory.client.CheckoutClient;
import com.compass.mshistory.client.CustomerClient;
import com.compass.mshistory.dto.CompraMessageDto;
import com.compass.mshistory.entity.Compra;
import com.compass.mshistory.entity.Historico;
import com.compass.mshistory.entity.Variacao;
import com.compass.mshistory.repository.CompraRepository;
import com.compass.mshistory.repository.HistoricoRepository;
import com.compass.mshistory.repository.VariacaoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HistoricoServiceImpleTest {

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private VariacaoRepository variacaoRepository;

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
    void deveriaRetornarUm(){

        Compra compra = criaCompra();
        Historico historico = criaHistorico();
        CompraMessageDto compraMessageDto = criaCompraMessageDto();

        Mockito.when(historicoRepository.findByUserId(compraMessageDto.getUserId())).thenReturn(Optional.of(historico));
        Mockito.when(modelMapper.map(compraMessageDto, Compra.class)).thenReturn(compra);
        Mockito.when(compraRepository.save(compra)).thenReturn(compra);

        historicoService.insereNovaCompra(compraMessageDto);

        verify(historicoRepository, times(1)).save(historico);


    }

    private CompraMessageDto criaCompraMessageDto() {
        return new CompraMessageDto(1L, 1L, BigDecimal.ZERO, LocalDate.now(), new ArrayList<>());
    }

    private Historico criaHistorico() {
        List<Compra> compras = new ArrayList<>();
        compras.add(criaCompra());
        return new Historico("id", 1L, compras);
    }

    private Variacao criaVariacao(){
        return new Variacao("id", "variant_id", 2);
    }

    private Compra criaCompra() {
        List<Variacao> variacoes = new ArrayList<>();
        variacoes.add(criaVariacao());
        return new Compra("id" , 1L, BigDecimal.ZERO, LocalDate.now() , variacoes);
    }

}