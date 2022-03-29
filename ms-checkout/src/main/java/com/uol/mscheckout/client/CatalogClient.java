package com.uol.mscheckout.client;

import com.uol.mscheckout.dto.VariacaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ms-catalog")
public interface CatalogClient {

    @GetMapping("v1/variations/{id}")
    VariacaoDto findById(@PathVariable String id);
}
