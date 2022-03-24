package com.compass.uol.customer.mscustomer.entity;

import com.compass.uol.customer.mscustomer.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Sexo sex;
    private String cpf;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Boolean active;


}
