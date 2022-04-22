package compass.uol.bffshop.client;

import compass.uol.bffshop.dto.UsuarioDto;
import compass.uol.bffshop.dto.UsuarioFormDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("customer")
public interface CustomerClient {

    @GetMapping("/v1/users/{id}")
    UsuarioDto buscaUser (@PathVariable Long id);

    @PutMapping("/v1/users/{id}")
    UsuarioDto atualizaUser (@PathVariable Long id, @RequestBody UsuarioFormDto usuarioFormDto);

    @PostMapping("/v1/users")
    UsuarioDto criaUser (@RequestBody UsuarioFormDto usuarioFormDto);


}