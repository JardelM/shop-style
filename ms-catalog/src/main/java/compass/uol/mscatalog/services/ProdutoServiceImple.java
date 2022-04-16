package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.dto.ProdutoFormDto;
import compass.uol.mscatalog.entity.Categoria;
import compass.uol.mscatalog.entity.Produto;
import compass.uol.mscatalog.exceptions.CategoryNotActiveException;
import compass.uol.mscatalog.exceptions.CategoryNotFoundException;
import compass.uol.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.repository.CategoriaRepository;
import compass.uol.mscatalog.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImple implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProdutoServiceImple (ProdutoRepository produtoRepository , CategoriaRepository categoriaRepository , ModelMapper modelMapper){
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProdutoDto createProduct(ProdutoFormDto produtoFormDto) {

        List<Categoria> categorias = verificaCategorias(produtoFormDto);
        Produto produtoACriar = modelMapper.map(produtoFormDto , Produto.class); //duvida em como se da a conversao com atributos diferentes
        Produto produtoCriado = produtoRepository.save(produtoACriar);

        categorias.forEach(categoria -> {
            adicionaProdutoACategoria(produtoCriado , categoria);
        });

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
        return modelMapper.map(produto , ProdutoDto.class);
    }



    @Override
    public ProdutoDto updateProduct(String id, ProdutoFormDto produtoFormDto) {

        verificaExistenciaProduto(id);
        List<Categoria> categorias = verificaCategorias(produtoFormDto);
        Produto produtoAAtualizar = modelMapper.map(produtoFormDto , Produto.class);
        produtoAAtualizar.setId(id);
        Produto produtoAtualizado = produtoRepository.save(produtoAAtualizar);

        atualizaCategorias(categorias, produtoAtualizado);

        return modelMapper.map(produtoAtualizado , ProdutoDto.class);
    }



    @Override
    public void deleteProduct(String id) {

        Produto produto = verificaExistenciaProduto(id);

        List<Categoria> categorias = categoriaRepository.findAllByProductsId(produto.getId());
        categorias.forEach(categoria -> {
            categoria.getProducts().remove(produto);
            categoriaRepository.save(categoria);
        });
        produtoRepository.deleteById(id);

    }

    private List<Categoria> verificaCategorias(ProdutoFormDto produtoFormDto){
        List<Categoria> categorias = new ArrayList<>();
        List<String> categoria_ids = produtoFormDto.getCategory_ids();

        categoria_ids.forEach(id ->{
            Categoria categoria = verificaExistenciaCategoria(id);
            verificaSeCategoriaEstaAtiva(id, categoria);
            categorias.add(categoria);
        });
        return categorias;
    }

    private void verificaSeCategoriaEstaAtiva(String id, Categoria categoria) {
        if(!categoria.getActive())
            throw new CategoryNotActiveException(id);
    }

    private Categoria verificaExistenciaCategoria(String id) {
        return categoriaRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
    }

    private void adicionaProdutoACategoria(Produto produtoCriado, Categoria categoria) {
        categoria.getProducts().add(produtoCriado);
        categoriaRepository.save(categoria);
    }

    private Produto verificaExistenciaProduto(String id) {
        return produtoRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    private void atualizaCategorias(List<Categoria> categorias, Produto produtoAtualizado) {
        categorias.forEach(categoria -> {
            if (!categoria.getProducts().contains(produtoAtualizado))
                adicionaProdutoACategoria(produtoAtualizado , categoria);

        });
        removeProdutoDeCategoria(categorias , produtoAtualizado);
    }

    private void removeProdutoDeCategoria(List<Categoria> categorias, Produto produtoAtualizado) {
        List<Categoria> todasCategorias = categoriaRepository.findAll();
        todasCategorias.stream()
                .filter(categoria -> !categorias.contains(categoria))
                .forEach(categoria -> {
                    categoria.getProducts().remove(produtoAtualizado);
                    categoriaRepository.save(categoria);
                });
    }
}
