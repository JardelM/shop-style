package com.uol.mscheckout.service;

import com.uol.mscheckout.client.CatalogClient;
import com.uol.mscheckout.client.CustomerClient;
import com.uol.mscheckout.dto.*;
import com.uol.mscheckout.entity.Pagamento;
import com.uol.mscheckout.exceptions.*;
import com.uol.mscheckout.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServiceImple implements CompraService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private MQServiceImpl mqService;


    @Override
    public void createPurchase(CompraFormDto compraFormDto) {

        UsuarioDto usuarioDto = customerClient.findbyId(compraFormDto.getUser_id());
        if(!usuarioDto.getActive()){
            throw new UserNotActiveException(compraFormDto.getUser_id());
        }

        Pagamento pagamento = pagamentoRepository.findById(compraFormDto.getPayment_id())
                .orElseThrow(()-> new PagamentoNotFoundException(compraFormDto.getPayment_id()));

        if (!pagamento.getStatus()){
            throw new PagamentoNotActiveException(compraFormDto.getUser_id());
        }

        List<CarrinhoFormDto> cart = compraFormDto.getCart();
        cart.forEach(item ->{
            VariacaoDto variacao = catalogClient.findById(item.getVariant_id());

            if(!variacao.getProduct().getActive()){
                throw new ProdutoNotActiveException(variacao.getProduct().getId());
            }

            if(item.getQuantity() > variacao.getQuantity()){
                throw new EstoqueInsuficienteException(item.getVariant_id());
            }

        });


        VariacaoMessageDto variacaoMessageDto = new VariacaoMessageDto();

        cart.forEach(item ->{
            variacaoMessageDto.setId(item.getVariant_id());
            variacaoMessageDto.setQuantity(item.getQuantity());
            mqService.messageToCatalog(variacaoMessageDto);
        });



    }




}
