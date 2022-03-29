package compass.uol.mscatalog.consumer;

import compass.uol.mscatalog.config.MQConfig;
import compass.uol.mscatalog.dto.VariacaoMessageDto;
import compass.uol.mscatalog.entity.Variacao;
import compass.uol.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.repository.VariacaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final VariacaoRepository variacaoRepository;

    @Autowired
    public MessageListener (VariacaoRepository variacaoRepository){
        this.variacaoRepository = variacaoRepository;
    }

    @RabbitListener (queues = MQConfig.QUEUE)
    public void listener (VariacaoMessageDto variacaoMessageDto){
        Variacao variacao = variacaoRepository.findById(variacaoMessageDto.getId())
                .orElseThrow(()-> new VariacaoNotFoundException(variacaoMessageDto.getId()));

        Integer quantity = variacao.getQuantity() - variacaoMessageDto.getQuantity();
        variacao.setQuantity(quantity);
        variacaoRepository.save(variacao);
    }
}
