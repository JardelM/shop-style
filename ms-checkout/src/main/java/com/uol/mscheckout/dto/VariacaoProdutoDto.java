package com.uol.mscheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProdutoDto {

    private String id;
    private BigDecimal price;
    private Integer quantity;
    private String product_id;
}
