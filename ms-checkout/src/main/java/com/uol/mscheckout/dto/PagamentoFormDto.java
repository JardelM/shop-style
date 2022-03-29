package com.uol.mscheckout.dto;

import com.uol.mscheckout.entity.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoFormDto {

    @Enumerated(EnumType.STRING)
    @NotNull (message = "forma de pagamento invalida")
    private TipoPagamento type;
    @NotNull
    private BigDecimal discount;
    @NotNull
    private Boolean status;
}
