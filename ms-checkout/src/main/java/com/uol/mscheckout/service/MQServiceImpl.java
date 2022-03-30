package com.uol.mscheckout.service;

import com.uol.mscheckout.config.MQConfiguracion;
import com.uol.mscheckout.dto.CompraMessageDto;
import com.uol.mscheckout.dto.VariacaoMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MQServiceImpl {

    private RabbitTemplate template;

    @Autowired
    public MQServiceImpl(RabbitTemplate template){
        this.template = template;
    }

    public void publishMessageToCatalog (List<VariacaoMessageDto> variacaoMessageDtos){
        template.convertAndSend(MQConfiguracion.EXCHANGE , MQConfiguracion.CATALOG_ROUTING_KEY , variacaoMessageDtos);
    }

    public void publishMessageTOHistory (CompraMessageDto compraMessageDto){
        template.convertAndSend(MQConfiguracion.EXCHANGE , MQConfiguracion.HISTORY_ROUTING_KEY , compraMessageDto);

    }

}
