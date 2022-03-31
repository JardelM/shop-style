package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.entity.Categoria;
import compass.uol.mscatalog.entity.Produto;
import compass.uol.mscatalog.exceptions.CategoryNotFoundException;
import compass.uol.mscatalog.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImple implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaServiceImple(CategoriaRepository categoryRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoriaDto createCategory(CategoriaFormDto body) {

        Categoria categoriaACriar = modelMapper.map( body , Categoria.class);
        Categoria categoriaCriada = categoriaRepository.save(categoriaACriar);
        return modelMapper.map(categoriaCriada , CategoriaDto.class);
    }

    @Override
    public List<CategoriaDto> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProdutoDto> findProductsFromCategory(String id) {
        Categoria categoria = veficaExistenciaDeId(id);

        List<Produto> produtos = categoria.getProducts();
        return produtos.stream().map(produto -> modelMapper.map(produto , ProdutoDto.class)).collect(Collectors.toList());
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

    private Categoria veficaExistenciaDeId(String id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
