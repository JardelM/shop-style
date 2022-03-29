package com.uol.mscheckout.dto;

import com.uol.mscheckout.entity.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto {

    private Long id;
    private TipoPagamento type;
    private BigDecimal discount;
    private Boolean status;
}
