package compass.uol.mscatalog.mscatalog.consumer;

import compass.uol.mscatalog.mscatalog.config.MQConfig;
import compass.uol.mscatalog.mscatalog.dto.VariacaoMessageDto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.mscatalog.repository.VariacaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //auto gerenciada pelo spring
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

        Integer quantidade = variacao.getQuantity() - variacaoMessageDto.getQuantity();
        variacao.setQuantity(quantidade);
        variacaoRepository.save(variacao);
    }


}
