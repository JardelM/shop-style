package com.uol.mscheckout.controller;


import com.uol.mscheckout.dto.CompraFormDto;
import com.uol.mscheckout.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/purchases")
public class CompraController {

    private final CompraService service;

    @Autowired
    public CompraController(CompraService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CompraFormDto compraFormDto){
        this.service.createPurchase(compraFormDto);
    }


}
