package com.uol.mscheckout.client;

import com.uol.mscheckout.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mscustomer")
public interface CustomerClient {

    @GetMapping("/v1/users/{id}")
    UsuarioDto findbyId(@PathVariable Long id);
}
