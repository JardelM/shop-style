package com.uol.mscheckout.exceptions;

import static java.lang.String.format;

public class PagamentoNotActiveException extends RuntimeException {
    public PagamentoNotActiveException(Long user_id) {
        super(format("Pagamento de id %s inativo"));
    }
}
