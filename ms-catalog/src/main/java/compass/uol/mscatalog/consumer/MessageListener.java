package compass.uol.mscatalog.consumer;

import compass.uol.mscatalog.config.MQConfiguration;
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

    @RabbitListener (queues = MQConfiguration.QUEUE)
    public void listener (List<VariacaoMessageDto> variacoesMessageDto){

        variacoesMessageDto.forEach(variacaoMessageDto -> {
            Variacao variacao = variacaoRepository.findById(variacaoMessageDto.getVariant_id())
                    .orElseThrow(()-> new VariacaoNotFoundException(variacaoMessageDto.getVariant_id()));
            Integer quantidade = variacao.getQuantity() - variacaoMessageDto.getQuantity();
            variacao.setQuantity(quantidade);
            variacaoRepository.save(variacao);
        });
    }
}
