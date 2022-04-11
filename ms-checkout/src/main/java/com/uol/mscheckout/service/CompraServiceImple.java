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

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private CatalogClient catalogClient;
    @Autowired
    private MQServiceImpl mqService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void createPurchase(CompraFormDto compraFormDto) {

        BigDecimal totalCusto = BigDecimal.ZERO;

        UsuarioAtivoDto usuario = customerClient.findById(compraFormDto.getUser_id());

        verificaSeUsuarioEstaAtivo(compraFormDto.getUser_id(), usuario.getActive());

        Pagamento pagamento = pagamentoRepository.findById(compraFormDto.getPayment_id())
                .orElseThrow(()-> new PagamentoNotFoundException(compraFormDto.getPayment_id()));

        verificaSePagamentoEstaAtivo(compraFormDto.getPayment_id(), pagamento.getStatus());

        List<CarrinhoFormDto> cart = compraFormDto.getCart();

        for (CarrinhoFormDto item : cart){

            VariacaoProdutoDto variacao = catalogClient.getById(item.getVariant_id());
            ProdutoAtivoDto produto = catalogClient.findById(variacao.getProduct_id());

            verificaSeProdutoEstaAtivo(produto.getId() , produto.getActive());

            verificaDisponibilidadeEstoque(item.getQuantity() , variacao.getQuantity(), variacao.getId());

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

    private void verificaDisponibilidadeEstoque(Integer quantidadeItem, Integer quantidadeVariacao, String id) {
        if (quantidadeItem > quantidadeVariacao)
            throw new EstoqueInsuficienteException(id);
    }

    private void verificaSeProdutoEstaAtivo(String id, Boolean active) {
        if (!active)
            throw new ProdutoNotActiveException(id);
    }

    private void verificaSePagamentoEstaAtivo(Long payment_id, Boolean status) {
        if (!status)
            throw new PagamentoNotActiveException(payment_id);
    }

    private void verificaSeUsuarioEstaAtivo(Long id, Boolean active) {
        if (!active)
            throw new UserNotActiveException(id);
    }


}
