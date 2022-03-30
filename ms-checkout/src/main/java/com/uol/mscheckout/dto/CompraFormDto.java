package com.uol.mscheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraFormDto {

    @NotNull
    private Long user_id;
    @NotNull
    private Long payment_id;
    @NotNull
    private List<@Valid CarrinhoFormDto> cart;
}
