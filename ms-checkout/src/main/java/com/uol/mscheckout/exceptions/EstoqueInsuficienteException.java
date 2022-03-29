package com.uol.mscheckout.exceptions;

import javax.validation.constraints.NotBlank;

import static java.lang.String.format;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(@NotBlank String variant_id) {
        super(format("Produto de id %s com estoque insuficiente" , variant_id));
    }
}
