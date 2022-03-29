package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.dto.ProdutoFormDto;

import java.util.List;

public interface ProdutoService {

    ProdutoDto createProduct(ProdutoFormDto body);

    List<ProdutoDto> findAll();

    ProdutoDto getProduct(String id);

    ProdutoDto updateProduct(String id , ProdutoFormDto body);

    void deleteProduct(String id);
}
