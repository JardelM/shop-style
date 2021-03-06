package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.dto.VariacaoProdutoDto;
import compass.uol.mscatalog.entity.Produto;
import compass.uol.mscatalog.entity.Variacao;
import compass.uol.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.repository.ProdutoRepository;
import compass.uol.mscatalog.repository.VariacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariacaoServiceImple implements VariacaoService {

    @Autowired
    private VariacaoRepository variacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VariacaoDto createVariation(VariacaoFormDto body) {

        Produto produto = verificaExistenciaProduto(body.getProduct_id());
        Variacao variacaoACriar = modelMapper.map(body , Variacao.class);
        Variacao variacaoCriada = variacaoRepository.save(variacaoACriar);

        produto.getVariations().add(variacaoCriada);
        produtoRepository.save(produto);
        return modelMapper.map(variacaoCriada , VariacaoDto.class);

    }

    @Override
    public VariacaoProdutoDto getVariation(String id) {

        Variacao variacao = verificaExistenciaVariacao(id);
        Optional<Produto> produto = produtoRepository.findByVariationsId(id);
        VariacaoProdutoDto variacaoProdutoDto = modelMapper.map(variacao , VariacaoProdutoDto.class);

        produto.ifPresent(value -> variacaoProdutoDto.setProduct_id(value.getId()));
        return variacaoProdutoDto;

    }

    @Override
    public VariacaoDto updateVariation(String id, VariacaoFormDto variacaoFormDto) {
        Variacao variacao = verificaExistenciaVariacao(id);
        Produto produto = verificaExistenciaProduto(variacaoFormDto.getProduct_id());

        Variacao variacaoACriar = modelMapper.map(variacaoFormDto , Variacao.class);
        //variacaoACriar.setId(id); acho que esta dando errado por nao setar aqui
        Variacao variacaoCriada = variacaoRepository.save(variacaoACriar);

        if(!produto.getVariations().contains(variacao))
            produto.getVariations().add(variacaoCriada);

        produtoRepository.save(produto);

        return modelMapper.map(variacaoCriada , VariacaoDto.class);

    }

    @Override
    public void deleteVariation(String id) {

        Variacao variacao = verificaExistenciaVariacao(id);

        Optional<Produto> produto = produtoRepository.findByVariationsId(variacao.getId());

        produto.ifPresent(val ->{
            val.getVariations().remove(variacao);
            produtoRepository.save(val);
        });
        variacaoRepository.deleteById(id);
    }




    private Produto verificaExistenciaProduto (String id){
        return produtoRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    private Variacao verificaExistenciaVariacao (String id){
        return variacaoRepository.findById(id).orElseThrow(()-> new VariacaoNotFoundException(id));
    }
}
