package com.uol.mscheckout.service;

import com.uol.mscheckout.dto.PagamentoDto;
import com.uol.mscheckout.dto.PagamentoFormDto;
import com.uol.mscheckout.entity.Pagamento;
import com.uol.mscheckout.entity.TipoPagamento;
import com.uol.mscheckout.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoServiceImpleTest {

    @MockBean
    private PagamentoRepository pagamentoRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private PagamentoServiceImple service;


    @Test
    void dadoUmIdDeveRetornarUmPagamento(){

        Pagamento pagamento = criaPagamento();
        PagamentoDto pagamentoEsperadoDto = criaPagamentoDto();

        when(pagamentoRepository.findById(pagamento.getId())).thenReturn(Optional.of(pagamento));
        when(modelMapper.map(pagamento , PagamentoDto.class)).thenReturn(pagamentoEsperadoDto);

        PagamentoDto pagamentoAtualDto = service.getById(pagamento.getId());
        assertEquals(pagamentoEsperadoDto , pagamentoAtualDto);

    }

    @Test
    void deveriaRetornarCreatedAoCriarPagamento(){

        Pagamento pagamento = criaPagamento();
        PagamentoFormDto pagamentoFormDto = criaPagamentoFormDto();
        PagamentoDto pagamentoEsperadoDto = criaPagamentoDto();

        when(modelMapper.map(pagamentoFormDto , Pagamento.class)).thenReturn(pagamento);
        when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);
        when(modelMapper.map(pagamento , PagamentoDto.class)).thenReturn(pagamentoEsperadoDto);

        PagamentoDto pagamentoAtualDto = service.createPayment(pagamentoFormDto);
        assertEquals(pagamentoEsperadoDto , pagamentoAtualDto);


    }

    @Test
    void deveriaDeletarUmPagamento(){
        Long id = 1L;
        Pagamento pagamento = criaPagamento();

        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamento));
        doNothing().when(pagamentoRepository).deleteById(id);
        service.deletePayment(id);
        verify(pagamentoRepository , times(1)).deleteById(id);

    }

    @Test
    void deveriaRetornarListaDePagamentos(){
        Pagamento pagamento = criaPagamento();
        PagamentoDto pagamentoDtoEsperado = criaPagamentoDto();

        when(pagamentoRepository.findAll()).thenReturn(Collections.singletonList(pagamento));
        when(modelMapper.map(pagamento , PagamentoDto.class)).thenReturn(pagamentoDtoEsperado);

        List<PagamentoDto> listaAtual = service.getAll();
        assertEquals(Collections.singletonList( pagamentoDtoEsperado ) , listaAtual);

    }
    
    @Test
    void deveriaAtualizarUmPagamentoComSucesso(){
        Long id = 1L;
        Pagamento pagamento = criaPagamento();
        PagamentoDto pagamentoDtoEsperado = criaPagamentoDto();
        PagamentoFormDto pagamentoFormDto = criaPagamentoFormDto();

        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamento));
        when(modelMapper.map(pagamentoFormDto , Pagamento.class)).thenReturn(pagamento);
        when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);
        when(modelMapper.map(pagamento , PagamentoDto.class)).thenReturn(pagamentoDtoEsperado);

        PagamentoDto pagamentoAtual = service.updatePayment(id , pagamentoFormDto);
        assertEquals(pagamentoAtual , pagamentoDtoEsperado);


    }

    private PagamentoDto criaPagamentoDto() {
        return new PagamentoDto(
                1L,
                TipoPagamento.PIX,
                new BigDecimal(5),
                true
        );
    }

    private Pagamento criaPagamento(){
        return new Pagamento(
                1L,
                TipoPagamento.PIX,
                new BigDecimal(5),
                true
        );
    }

    private PagamentoFormDto criaPagamentoFormDto() {
        return new PagamentoFormDto(
                TipoPagamento.PIX,
                new BigDecimal(5),
                true
        );
    }

}