package com.compass.uol.customer.mscustomer.dto;

import com.compass.uol.customer.mscustomer.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFormDto {

    @NotBlank @Length (min = 3, message = "Deve ter pelo menos 3 caracteres")
    private String firstName;
    @NotBlank @Length (min = 3, message = "Deve ter pelo menos 3 caracteres")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @NotNull (message = "Deve preencher com Masculino ou feminino")
    private Sexo sex;
    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;
    @Email (message = "Deve ser um email válido")
    private String email;
    @NotBlank @Length (min = 8 , message = "Deve ter pelo menos 8 caracteres")
    private String password;
    @NotNull
    private Boolean active;
}
