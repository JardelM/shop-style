package com.compass.mshistory.service;

import com.compass.mshistory.client.CatalogClient;
import com.compass.mshistory.client.CheckoutClient;
import com.compass.mshistory.client.CustomerClient;
import com.compass.mshistory.dto.*;
import com.compass.mshistory.entity.Compra;
import com.compass.mshistory.entity.Historico;
import com.compass.mshistory.entity.Variacao;
import com.compass.mshistory.exceptions.HistoricoNotFoundException;
import com.compass.mshistory.repository.CompraRepository;
import com.compass.mshistory.repository.HistoricoRepository;
import com.compass.mshistory.repository.VariacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoServiceImple implements HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private VariacaoRepository variacaoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private CheckoutClient checkoutClient;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public void insereNovaCompra(CompraMessageDto compraMessageDto) {

        Historico historico = historicoRepository.findByUserId(compraMessageDto.getUserId())
                .orElse(new Historico(compraMessageDto.getUserId()));

        Compra compraASalvar = modelMapper.map(compraMessageDto , Compra.class);

        List<Variacao> variacoesASalvar = compraASalvar.getVariations();
        List<Variacao> variacoesSalvas = variacoesASalvar.stream().map(variacao -> variacaoRepository.save(variacao)).collect(Collectors.toList());//.toList();


        compraASalvar.setVariations(variacoesSalvas);

        System.out.println("As variacoes salvas são? :"+variacoesSalvas);

        Compra compraSalva = compraRepository.save(compraASalvar);

        historico.getPurchases().add(compraSalva);
        historicoRepository.save(historico);
    }



    @Override
    public HistoricoDto findUserHistoric(Long userId) {

        List<CompraDto> comprasDto = new ArrayList<>();

        Historico historico = historicoRepository.findByUserId(userId).orElseThrow(()->new HistoricoNotFoundException(userId));

        UsuarioDto usuario = customerClient.getUser(historico.getUserId());

        List<Compra> compras = historico.getPurchases();

        for (Compra compra : compras){

            PagamentoDto pagamento = checkoutClient.findById(compra.getPaymentId());

            List<Variacao> variacoes = compra.getVariations();

            BigDecimal total = compra.getTotal();

            LocalDate date = compra.getDate();


            List<ProdutoDto> produtos = variacoes.stream()
                    .map(variacao -> {

                        VariacaoClientDto variacaoClientDto = catalogClient.getVariation(variacao.getVariant_id());
                        System.out.println("qtd variação :"+variacao.getQuantity());
                        System.out.println("qtd variaçãoClientDto :"+variacaoClientDto.getQuantity());
                        ProdutoDto produto = catalogClient.getProduct(variacaoClientDto.getProduct_id());

                        produto.setColor(variacaoClientDto.getColor());
                        produto.setSize(variacaoClientDto.getSize());
                        produto.setPrice(variacaoClientDto.getPrice());
                        produto.setQuantity(variacao.getQuantity());
                        return produto;
                    }).toList();


            comprasDto.add(new CompraDto(
                    pagamento,
                    produtos,
                    total,
                    date
            ));



        }

        return new HistoricoDto(
                usuario,
                comprasDto
        );
    }


}


