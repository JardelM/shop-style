package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;

import java.util.List;

public interface CategoriaService {

    CategoriaDto createCategory(CategoriaFormDto body);

    List<CategoriaDto> findAll();

    CategoriaDto updateCategory(String id, CategoriaFormDto body);

    void delete(String id);

    List<ProdutoDto> findProductsFromCategory(String id);
}
