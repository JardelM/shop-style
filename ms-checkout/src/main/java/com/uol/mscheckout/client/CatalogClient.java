package com.uol.mscheckout.client;

import com.uol.mscheckout.dto.ProdutoAtivoDto;
import com.uol.mscheckout.dto.VariacaoProdutoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("catalog")
public interface CatalogClient {

    @GetMapping("/v1/variations/{id}")
    VariacaoProdutoDto getById (@PathVariable String id);

    @GetMapping("/v1/products/{id}")
    ProdutoAtivoDto findById (@PathVariable String id);


}
