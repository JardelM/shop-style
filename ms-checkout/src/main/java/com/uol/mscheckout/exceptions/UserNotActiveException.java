package com.uol.mscheckout.exceptions;

import static java.lang.String.format;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(Long user_id) {
        super(format("Usuario de id %s não está ativo", user_id));
    }
}
