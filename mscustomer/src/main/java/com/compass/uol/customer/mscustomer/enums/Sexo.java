package com.compass.uol.customer.mscustomer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexo {

    MASCULINO ("Masculino"),
    FEMININO ("Feminino");

    private final String descricao;

    @JsonCreator (mode = JsonCreator.Mode.DELEGATING)
    public static Sexo paraValores (@JsonProperty ("sex") String des){
        for (Sexo sex : Sexo.values()){
            if (sex.descricao.equalsIgnoreCase(des))
                return sex;
        }
        return null;
    }
}
