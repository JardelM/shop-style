package com.uol.mscheckout.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoFormDto {

    @NotBlank
    private String variant_id;

    @NotNull
    @Positive
    private Integer quantity;
}
