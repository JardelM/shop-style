package com.compass.mshistory.client;

import com.compass.mshistory.dto.ProdutoDto;
import com.compass.mshistory.dto.VariacaoClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("catalog")
public interface CatalogClient {

    @GetMapping("/v1/variations/{id}")
    VariacaoClientDto getVariation(@PathVariable String id);

    @GetMapping("/v1/products/{id}")
    ProdutoDto getProduct(@PathVariable String id);
}
