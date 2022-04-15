package compass.uol.bffshop.client;

import compass.uol.bffshop.dto.CompraFormDto;
import compass.uol.bffshop.dto.PagamentoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("checkout")
public interface CheckoutClient {

    @GetMapping("/v1/payments")
    List<PagamentoDto> buscaPagamentos();

    @PostMapping("v1/purchases")
    void criaCompra(@RequestBody CompraFormDto compraFormDto);
}
