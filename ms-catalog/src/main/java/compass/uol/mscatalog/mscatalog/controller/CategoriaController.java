package compass.uol.mscatalog.mscatalog.controller;

import compass.uol.mscatalog.mscatalog.dto.CategoriaDto;
import compass.uol.mscatalog.mscatalog.dto.CategoriaFormDto;
import compass.uol.mscatalog.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.mscatalog.entity.Categoria;
import compass.uol.mscatalog.mscatalog.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/categories")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaDto createCategory (@RequestBody @Valid CategoriaFormDto body){
        return this.service.createCategory(body);
    }

    @GetMapping
    public List<CategoriaDto> findAll(){

        return this.service.findAll();
    }

    @GetMapping("/{id}/products")
    public List<ProdutoDto> findProductsByCategory(@PathVariable String id){
        return this.service.findProductsFromCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategory(@PathVariable String id, @RequestBody @Valid CategoriaFormDto body){
        CategoriaDto categoriaDto = this.service.updateCategory(id, body);
        return ResponseEntity.ok(categoriaDto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable String id){
        this.service.delete(id);

    }

}
