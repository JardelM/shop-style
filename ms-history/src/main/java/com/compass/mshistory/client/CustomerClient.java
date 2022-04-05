package com.compass.mshistory.client;

import com.compass.mshistory.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("customer") //nome que deve ser igual ao db (caso contrario, RetryableException..)
public interface CustomerClient {

    @GetMapping("/v1/users/{id}")
    UsuarioDto getUser (@PathVariable Long id); //o nome do metodo nao tem que ser igual ao original da classe customer
}
