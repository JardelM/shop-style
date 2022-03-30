package com.uol.mscheckout.client;

import com.uol.mscheckout.dto.UsuarioAtivoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("customer")
public interface CustomerClient {

    @GetMapping("v1/users/{id}")
    UsuarioAtivoDto findById(@PathVariable Long id);

}
