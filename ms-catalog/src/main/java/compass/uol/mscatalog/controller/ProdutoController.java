package compass.uol.mscatalog.controller;

import compass.uol.mscatalog.dto.ProdutoDto;
import compass.uol.mscatalog.dto.ProdutoFormDto;
import compass.uol.mscatalog.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto createProduct (@RequestBody @Valid ProdutoFormDto body){
        return this.service.createProduct(body);
    }

    @GetMapping
    public List<ProdutoDto> findProducts(){
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoDto getProduct(@PathVariable String id){
        return this.service.getProduct(id);
    }

    @PutMapping("/{id}")
    public ProdutoDto updateProduct (@PathVariable String id, @RequestBody @Valid ProdutoFormDto body){
        return this.service.updateProduct(id , body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable String id){
        this.service.deleteProduct(id);
    }



}
