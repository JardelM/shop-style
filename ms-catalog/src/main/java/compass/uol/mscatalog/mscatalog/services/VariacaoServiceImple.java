package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.mscatalog.exceptions.VariacaoNotFoundException;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import compass.uol.mscatalog.mscatalog.repository.VariacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public VariacaoDto updateVariation(String id, VariacaoFormDto variacaoFormDto) {
        verificaExistenciaVariacao(id);
        verificaExistenciaProduto(variacaoFormDto.getProduct_id());

        Variacao variacaoACriar = modelMapper.map(variacaoFormDto , Variacao.class);
        variacaoACriar.setId(id);
        Variacao variacaoCriada = variacaoRepository.save(variacaoACriar);

        return modelMapper.map(variacaoCriada , VariacaoDto.class);

    }

    @Override
    public void deleteVariation(String id) {
        verificaExistenciaVariacao(id);
        variacaoRepository.deleteById(id);
    }

    @Override
    public VariacaoDto getVariation(String id) {
        Variacao variacao = verificaExistenciaVariacao(id);
        return modelMapper.map(variacao , VariacaoDto.class);
    }

    private Produto verificaExistenciaProduto (String id){
        return produtoRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    private Variacao verificaExistenciaVariacao (String id){
        return variacaoRepository.findById(id).orElseThrow(()-> new VariacaoNotFoundException(id));
    }
}
