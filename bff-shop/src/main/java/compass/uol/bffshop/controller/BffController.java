package compass.uol.bffshop.controller;

import compass.uol.bffshop.client.CatalogClient;
import compass.uol.bffshop.client.CheckoutClient;
import compass.uol.bffshop.client.CustomerClient;
import compass.uol.bffshop.client.HistoryClient;
import compass.uol.bffshop.dto.*;
import compass.uol.bffshop.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class BffController {


    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private CheckoutClient checkoutClient;

    @Autowired
    private HistoryClient historyClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> autenticar (@RequestBody @Valid LoginFormDto loginFormDto){

        UsernamePasswordAuthenticationToken dadosLogin = loginFormDto.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Barer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }






    //customer
    @GetMapping("/users/{id}")
    public UsuarioDto retornaUsuario(@PathVariable Long id){
        return customerClient.buscaUser(id);
    }

    @PutMapping(("/users/{id}"))
    public UsuarioDto atualizaUsuario(@PathVariable Long id, @RequestBody UsuarioFormDto usuarioFormDto){
        return customerClient.atualizaUser(id, usuarioFormDto);
    }

    @PostMapping(("/users"))
    public UsuarioDto criaUsuario(@RequestBody UsuarioFormDto usuarioFormDto){
        return customerClient.criaUser(usuarioFormDto);
    }

    //catalog
    @GetMapping("/products/{id}")
    public ProdutoDto retornaProduto (@PathVariable String id){
        return catalogClient.buscaProduto(id);
    }

    @GetMapping("/categories/{id}/products")
    public List<ProdutoDto> buscaProdutoPorCategoria(@PathVariable String id){
        return catalogClient.buscaProdutoPorCategoria(id);
    }

    //checkout
    @GetMapping("/payments")
    public List<PagamentoDto> buscaPagamentos(){
        return checkoutClient.buscaPagamentos();
    }

    @PostMapping("/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    void criaCrompra(@RequestBody CompraFormDto compraFormDto){
        checkoutClient.criaCompra(compraFormDto);
    }

    //history
    @GetMapping("/historic/user/{idUser}")
    public HistoricoDto buscaHistorico (@PathVariable Long idUser){
        return historyClient.buscaHistoricoUser(idUser);
    }


}
