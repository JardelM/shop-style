package com.compass.mshistory.service;

import com.compass.mshistory.dto.CompraDto;
import com.compass.mshistory.dto.CompraMessageDto;
import com.compass.mshistory.dto.HistoricoDto;
import com.compass.mshistory.entity.Historico;

import java.util.Optional;

public interface HistoricoService {


    HistoricoDto findUserHistoric(Long userId);

    void insereNovaCompra(CompraMessageDto compraDto);
}

