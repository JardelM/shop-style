package compass.uol.bffshop.client;

import compass.uol.bffshop.dto.ProdutoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("catalog")
public interface CatalogClient {

    @GetMapping("/v1/products/{id}")
    ProdutoDto buscaProduto(@PathVariable String id);

    @GetMapping("/v1/categories/{id}/products")
    List<ProdutoDto> buscaProdutoPorCategoria(@PathVariable String id);


}
