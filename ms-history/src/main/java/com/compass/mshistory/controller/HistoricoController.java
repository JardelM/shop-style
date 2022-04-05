package com.compass.mshistory.controller;

import com.compass.mshistory.dto.HistoricoDto;
import com.compass.mshistory.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/historic/user/")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;


    @GetMapping("/{userId}")
    public HistoricoDto findUserHistoric(@PathVariable Long userId){
        return historicoService.findUserHistoric(userId);
    }


}
