package com.compass.mshistory.exceptions;

import static java.lang.String.format;

public class HistoricoNotFoundException extends RuntimeException {
    public HistoricoNotFoundException(Long userId) {
        super(format("Não encontrado historico de usuario com id %s", userId));
    }
}
