package com.uol.mscheckout.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPagamento {

    CREDIT_CARD("Credit Card"),
    PIX("Pix");

    private final String descricao;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TipoPagamento paraValores (@JsonProperty("type") String des){
        for (TipoPagamento tipoPagamento : TipoPagamento.values()){
            if (tipoPagamento.descricao.equalsIgnoreCase(des))
                return tipoPagamento;
        }
        return null;
    }

}
