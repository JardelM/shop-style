package com.uol.mscheckout.service;

import com.uol.mscheckout.client.CatalogClient;
import com.uol.mscheckout.client.CustomerClient;
import com.uol.mscheckout.dto.*;
import com.uol.mscheckout.entity.Pagamento;
import com.uol.mscheckout.exceptions.*;
import com.uol.mscheckout.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CompraServiceImple implements CompraService {


    private final PagamentoRepository pagamentoRepository;

    private final CustomerClient customerClient;

    private final CatalogClient catalogClient;

    private final MQServiceImpl mqService;

    private final ModelMapper modelMapper;


    @Autowired
    public CompraServiceImple (CustomerClient customerClient , CatalogClient catalogClient , PagamentoRepository pagamentoRepository, MQServiceImpl mqService, ModelMapper modelMapper){
        this.customerClient = customerClient;
        this.catalogClient = catalogClient;
        this.pagamentoRepository = pagamentoRepository;
        this.mqService = mqService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void createPurchase(CompraFormDto compraFormDto) {

        BigDecimal totalCusto = BigDecimal.ZERO;

        UsuarioAtivoDto usuario = customerClient.findById(compraFormDto.getUser_id());

        if (!usuario.getActive())
            throw new UserNotActiveException(compraFormDto.getUser_id());

        Pagamento pagamento = pagamentoRepository.findById(compraFormDto.getPayment_id())
                .orElseThrow(()-> new PagamentoNotFoundException(compraFormDto.getPayment_id()));

        if (!pagamento.getStatus())
            throw new PagamentoNotActiveException(compraFormDto.getPayment_id());

        List<CarrinhoFormDto> cart = compraFormDto.getCart();

        for (CarrinhoFormDto item : cart){

            VariacaoProdutoDto variacao = catalogClient.getById(item.getVariant_id());
            ProdutoAtivoDto produto = catalogClient.findById(variacao.getProduct_id());

            if (!produto.getActive())
                throw new ProdutoNotActiveException(produto.getId());

            if (item.getQuantity() > variacao.getQuantity())
                throw new EstoqueInsuficienteException(variacao.getId());


            BigDecimal itemCusto = variacao.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCusto = totalCusto.add(itemCusto);


        }

        List<VariacaoMessageDto> variacaoMessageDtoList = cart.stream()
                .map(item-> modelMapper.map(item , VariacaoMessageDto.class))
                .toList();

        mqService.publishMessageToCatalog(variacaoMessageDtoList);


        CompraMessageDto compraMessageDto = new CompraMessageDto(
                compraFormDto.getUser_id(),
                compraFormDto.getPayment_id(),
                totalCusto,
                LocalDate.now(),
                variacaoMessageDtoList
        );

        mqService.publishMessageTOHistory(compraMessageDto);







    }




}
