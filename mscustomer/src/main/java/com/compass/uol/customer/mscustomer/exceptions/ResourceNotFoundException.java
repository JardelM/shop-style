package com.compass.uol.customer.mscustomer.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException (String msg){
        super(msg);
    }
}
