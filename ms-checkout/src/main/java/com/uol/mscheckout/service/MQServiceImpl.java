package com.uol.mscheckout.service;

import com.uol.mscheckout.config.MQConfiguracion;
import com.uol.mscheckout.dto.VariacaoMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQServiceImpl {

    @Autowired
    private RabbitTemplate template;

    public void messageToCatalog (VariacaoMessageDto variacaoMessageDto){
        template.convertAndSend(MQConfiguracion.QUEUE , variacaoMessageDto);
    }
}
