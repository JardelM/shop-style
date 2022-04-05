package com.compass.mshistory.client;

import com.compass.mshistory.dto.PagamentoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("checkout")
public interface CheckoutClient {

    @GetMapping("/v1/payments/{id}")
    PagamentoDto findById (@PathVariable Long id);
}
