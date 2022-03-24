package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoFormDto;
import compass.uol.mscatalog.mscatalog.entity.Categoria;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.exceptions.CategoryNotActiveException;
import compass.uol.mscatalog.mscatalog.exceptions.CategoryNotFoundException;
import compass.uol.mscatalog.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.mscatalog.repository.CategoriaRepository;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImple implements ProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProdutoDto createProduct(ProdutoFormDto body) {

        List<Categoria> categorias = verificaCategoria(body);
        Produto produto = modelMapper.map(body , Produto.class);

        Produto produtoCriado = produtoRepository.save(produto);
        atualizaCategorias (categorias , produtoCriado); //aumenta a lista de categorias

        return modelMapper.map(produtoCriado , ProdutoDto.class);

    }


    @Override
    public List<ProdutoDto> findAll() {

        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(produto -> modelMapper.map(produto , ProdutoDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProdutoDto getProduct(String id) {

        Produto produto = verificaExistenciaProduto(id);
        return modelMapper.map(produto, ProdutoDto.class);

    }

    @Override
    public ProdutoDto updateProduct(String id , ProdutoFormDto produtoFormDto) {

        verificaExistenciaProduto(id);
        List<Categoria> categorias = verificaCategoria(produtoFormDto);
        Produto produtoAAtualizar = modelMapper.map(produtoFormDto , Produto.class);
        produtoAAtualizar.setId(id);
        Produto produtoAtualizado = this.produtoRepository.save(produtoAAtualizar);
        return modelMapper.map(produtoAtualizado , ProdutoDto.class);

    }

    @Override
    public void deleteProduct(String id) {
        verificaExistenciaProduto(id);
        produtoRepository.deleteById(id);
    }

/*
    //adiciono as categorias ao produto
    private Produto setCategories (ProdutoFormDto produtoFormDto){
        List<String> categoriesId = new ArrayList<>(produtoFormDto.getCategory_ids());
        Produto produto = modelMapper.map(produtoFormDto, Produto.class);

        //categories id só tem strings de ids em si, devido ao comando get acima
        categoriesId.forEach(catId ->{
            Categoria categoria = verificaExistenciaId(catId);
            verificaSeIdEstaAtivo(catId, categoria);
            produto.getCategories().add(categoria);

        });
        return produto;
    }*/


    private Produto verificaExistenciaProduto (String id){
        return produtoRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    private List<Categoria> verificaCategoria(ProdutoFormDto produtoFormDto){
        List<String> categoriasId = new ArrayList<>(produtoFormDto.getCategory_ids());
        List<Categoria> categorias = new ArrayList<>();

        categoriasId.forEach(catId ->{
            Categoria categoria = verificaExistenciaCategoria(catId);
            verificaSeCategoriaEstaAtiva (catId, categoria);
            categorias.add(categoria);
        });
        return categorias;
    }

    private void verificaSeCategoriaEstaAtiva(String catId, Categoria categoria) {
        if (!categoria.getActive())
            throw new CategoryNotActiveException(catId);
    }

    private Categoria verificaExistenciaCategoria(String catId) {
        return categoriaRepository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId));
    }

    private void atualizaCategorias(List<Categoria> categorias, Produto produtoCriado) {

        categorias.forEach(categoria -> {
            if (!categoria.getProducts().contains(produtoCriado)){
                categoria.getProducts().add(produtoCriado);
                categoriaRepository.save(categoria);
            }
        });
        List<Categoria> todasCategorias = categoriaRepository.findAll();
        todasCategorias.stream().filter(categoria -> !categorias.contains(categoria))
                .forEach(categoria -> {
                    categoria.getProducts().remove(produtoCriado);
                    categoriaRepository.save(categoria);
                });
    }

}
