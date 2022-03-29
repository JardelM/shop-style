package compass.uol.mscatalog.controller;

import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.dto.VariacaoProdutoDto;
import compass.uol.mscatalog.services.VariacaoService;
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
