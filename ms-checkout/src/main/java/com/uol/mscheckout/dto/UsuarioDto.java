package com.uol.mscheckout.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String sex;
    private String cpf;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    private String email;
    private Boolean active;

}
