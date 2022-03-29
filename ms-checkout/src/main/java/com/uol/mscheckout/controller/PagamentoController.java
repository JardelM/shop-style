package com.uol.mscheckout.controller;

import com.uol.mscheckout.dto.PagamentoDto;
import com.uol.mscheckout.dto.PagamentoFormDto;
import com.uol.mscheckout.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/payments")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public List<PagamentoDto> findAll(){
        return this.service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoDto create(@RequestBody @Valid PagamentoFormDto pagamentoFormDto){
        return this.service.createPayment(pagamentoFormDto);
    }

    @GetMapping("/{id}")
    public PagamentoDto findById (@PathVariable Long id){
        return this.service.getById(id);
    }

    @PutMapping("/{id}")
    public PagamentoDto update (@PathVariable Long id, @RequestBody @Valid PagamentoFormDto pagamentoFormDto){
        return this.service.updatePayment(id , pagamentoFormDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        this.service.deletePayment(id);
    }

}
