package com.uol.mscheckout.service;

import com.uol.mscheckout.dto.PagamentoDto;
import com.uol.mscheckout.dto.PagamentoFormDto;

import java.util.List;

public interface PagamentoService {
    List<PagamentoDto> getAll();

    PagamentoDto createPayment(PagamentoFormDto pagamentoFormDto);

    PagamentoDto getById(Long id);

    PagamentoDto updatePayment(Long id, PagamentoFormDto pagamentoFormDto);

    void deletePayment(Long id);
}
