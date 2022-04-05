package com.compass.mshistory.consumer;


import com.compass.mshistory.config.MQConfiguration;
import com.compass.mshistory.dto.CompraMessageDto;
import com.compass.mshistory.service.HistoricoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private final HistoricoService historicoService;


    @Autowired
    public MessageListener(HistoricoService historicoService){
        this.historicoService = historicoService;
    }

    @RabbitListener(queues = MQConfiguration.QUEUE)
    public void listener (CompraMessageDto compraDto){

        System.out.println(compraDto);

        historicoService.insereNovaCompra(compraDto);
    }

}
