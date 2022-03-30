package com.uol.mscheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoAtivoDto {

    private String id;
    private Boolean active;
}
