package com.uol.mscheckout.service;

import com.uol.mscheckout.dto.PagamentoDto;
import com.uol.mscheckout.dto.PagamentoFormDto;
import com.uol.mscheckout.entity.Pagamento;
import com.uol.mscheckout.exceptions.PagamentoNotFoundException;
import com.uol.mscheckout.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoServiceImple implements PagamentoService{

    private final PagamentoRepository pagamentoRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PagamentoServiceImple (PagamentoRepository pagamentoRepository , ModelMapper modelMapper){
        this.pagamentoRepository = pagamentoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PagamentoDto> getAll() {

        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.stream().map(pagamento -> modelMapper.map(pagamento , PagamentoDto.class)).collect(Collectors.toList());

    }

    @Override
    public PagamentoDto createPayment(PagamentoFormDto pagamentoFormDto) {

        Pagamento pagamentoACriar = modelMapper.map(pagamentoFormDto , Pagamento.class);
        Pagamento pagamentoCriado = pagamentoRepository.save(pagamentoACriar);

        return modelMapper.map(pagamentoCriado , PagamentoDto.class);
    }

    @Override
    public PagamentoDto getById(Long id) {
        Pagamento pagamento = verificaExistenciaPagamento(id);
        return modelMapper.map(pagamento , PagamentoDto.class);

    }

    @Override
    public PagamentoDto updatePayment(Long id, PagamentoFormDto pagamentoFormDto) {
        verificaExistenciaPagamento(id);

        Pagamento pagamentoAAtualizar = modelMapper.map(pagamentoFormDto , Pagamento.class);
        pagamentoAAtualizar.setId(id);
        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamentoAAtualizar);
        return modelMapper.map(pagamentoAtualizado , PagamentoDto.class);
    }

    @Override
    public void deletePayment(Long id) {
        verificaExistenciaPagamento(id);
        pagamentoRepository.deleteById(id);
    }

    private Pagamento verificaExistenciaPagamento(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(()-> new PagamentoNotFoundException(id));
    }
}
