package com.compass.uol.customer.mscustomer.controller;


import com.compass.uol.customer.mscustomer.dto.UsuarioDto;
import com.compass.uol.customer.mscustomer.dto.UsuarioFormDto;
import com.compass.uol.customer.mscustomer.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto createUser (@RequestBody @Valid UsuarioFormDto usuarioFormDto){
        return this.service.createUser(usuarioFormDto);
    }

    @GetMapping("/{id}")
    public UsuarioDto getUser (@PathVariable Long id){

        return this.service.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity <UsuarioDto> updateUser (@PathVariable Long id, @RequestBody @Valid UsuarioFormDto body){
        UsuarioDto usuario = this.service.updateUser(id, body);
        return ResponseEntity.ok(usuario);
    }

}
