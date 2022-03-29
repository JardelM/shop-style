package compass.uol.mscatalog.mscatalog.controller;

import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoProdutoDto;
import compass.uol.mscatalog.mscatalog.entity.Variacao;
import compass.uol.mscatalog.mscatalog.services.VariacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/variations")
public class VariacaoController {

    @Autowired
    private VariacaoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VariacaoDto createVariation(@RequestBody VariacaoFormDto variacaoFormDto){
        return this.service.createVariation(variacaoFormDto);
    }

    @PutMapping("/{id}")
    public VariacaoDto updateVariation (@PathVariable String id , @RequestBody VariacaoFormDto variacaoFormDto){
        return this.service.updateVariation(id, variacaoFormDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariation(@PathVariable String id){
        this.service.deleteVariation(id);
    }

    @GetMapping("/{id}")
    public VariacaoProdutoDto getVariation(@PathVariable String id){
        return this.service.getVariation(id);
    }

}
