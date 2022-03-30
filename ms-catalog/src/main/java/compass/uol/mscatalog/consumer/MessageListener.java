package compass.uol.mscatalog.consumer;

import compass.uol.mscatalog.config.MQConfig;
import compass.uol.mscatalog.dto.VariacaoMessageDto;
import compass.uol.mscatalog.entity.Variacao;
import compass.uol.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.repository.VariacaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageListener {

    private final VariacaoRepository variacaoRepository;

    @Autowired
    public MessageListener (VariacaoRepository variacaoRepository){
        this.variacaoRepository = variacaoRepository;
    }


    // diminui a quantidade de variacao no estoque.
    @RabbitListener (queues = MQConfig.QUEUE)
     void listener (List<VariacaoMessageDto> variacoesMessageDto){

        variacoesMessageDto.forEach(variacaoMessage -> {
            Variacao variacao = variacaoRepository.findById(variacaoMessage.getVariant_id()).orElseThrow(()-> new VariacaoNotFoundException(variacaoMessage.getVariant_id()));

            Integer quantity = variacao.getQuantity() - variacaoMessage.getQuantity();
            variacao.setQuantity(quantity);
            variacaoRepository.save(variacao);
        });
    }


}
