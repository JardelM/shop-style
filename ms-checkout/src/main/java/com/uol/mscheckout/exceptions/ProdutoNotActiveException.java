package com.uol.mscheckout.exceptions;

import static java.lang.String.format;

public class ProdutoNotActiveException extends RuntimeException {
    public ProdutoNotActiveException(String id) {
        super(format("Produto de id %s não ativo" , id));
    }
}
