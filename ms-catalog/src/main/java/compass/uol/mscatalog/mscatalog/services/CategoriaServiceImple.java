package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.mscatalog.entity.Categoria;
import compass.uol.mscatalog.mscatalog.entity.Produto;
import compass.uol.mscatalog.mscatalog.exceptions.CategoryNotFoundException;
import compass.uol.mscatalog.mscatalog.repository.CategoriaRepository;
import compass.uol.mscatalog.mscatalog.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImple implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaServiceImple(CategoriaRepository categoryRepository, ProdutoRepository produtoRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoryRepository;
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoriaDto createCategory(CategoriaFormDto body) {

        Categoria categoriaACriar = modelMapper.map( body , Categoria.class);
        Categoria categoriaCriada = this.categoriaRepository.save(categoriaACriar);
        return modelMapper.map(categoriaCriada , CategoriaDto.class);
    }

    @Override
    public List<CategoriaDto> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoriaDto updateCategory(String id, CategoriaFormDto body) {

        veficaExistenciaDeId(id);
        Categoria categoriaAAtualizar = modelMapper.map(body , Categoria.class);
        categoriaAAtualizar.setId(id);
        Categoria categoriaAtualizada = categoriaRepository.save(categoriaAAtualizar);
        return modelMapper.map(categoriaAtualizada , CategoriaDto.class);
    }

    @Override
    public void delete(String id) {
        veficaExistenciaDeId(id);
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<ProdutoDto> findAllProductsByCategory(String id) {
        Categoria categoria = veficaExistenciaDeId(id);

        List<Produto> produtos = categoria.getProducts();
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDto.class)).collect(Collectors.toList());
    }


    private Categoria veficaExistenciaDeId(String id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
