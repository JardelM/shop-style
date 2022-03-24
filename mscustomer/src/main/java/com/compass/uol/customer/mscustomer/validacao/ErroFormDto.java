package com.compass.uol.customer.mscustomer.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErroFormDto {

    private String campo;
    private String erro;

}
