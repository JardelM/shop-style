package com.compass.uol.customer.mscustomer.dto;

import com.compass.uol.customer.mscustomer.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Sexo sex;
    private String cpf;
    @JsonFormat (pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;
    private String email;
    private String password;
    private Boolean active;
}
