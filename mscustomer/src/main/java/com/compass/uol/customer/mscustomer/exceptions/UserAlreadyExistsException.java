package com.compass.uol.customer.mscustomer.exceptions;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
    public UserAlreadyExistsException(String email) {
        super(String.format("O email %s já está cadastrado",email));
    }
}
