package com.uol.mscheckout.exceptions;

import static java.lang.String.format;

public class PagamentoNotFoundException extends RuntimeException {
    public PagamentoNotFoundException(Long id) {
        super(format("Não encontrado pagamento de id %s", id));
    }
}
