package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.ProdutoInfoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoProdutoDto;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import compass.uol.mscatalog.mscatalog.repository.VariacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.ObjectProvider;
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

        ProdutoInfoDto produtoInfoDto = null;
        Variacao variacao = verificaExistenciaVariacao(id);

        Optional<Produto> produto = produtoRepository.findByVariationsId(id);

        VariacaoProdutoDto variacaoProdutoDto = modelMapper.map(variacao , VariacaoProdutoDto.class);

        if (produto.isPresent())
            produtoInfoDto = modelMapper.map(produto.get() , ProdutoInfoDto.class);

        variacaoProdutoDto.setProduct(produtoInfoDto);
        return variacaoProdutoDto;

    }

    @Override
    public VariacaoDto updateVariation(String id, VariacaoFormDto variacaoFormDto) {
        Variacao variacao = verificaExistenciaVariacao(id);
        Produto produto = verificaExistenciaProduto(variacaoFormDto.getProduct_id());

        Variacao variacaoACriar = modelMapper.map(variacaoFormDto , Variacao.class);
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
