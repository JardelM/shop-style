package compass.uol.bffshop.client;

import compass.uol.bffshop.dto.HistoricoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("history")
public interface HistoryClient {

    @GetMapping("/v1/historic/user/{userId}")
    HistoricoDto buscaHistoricoUser (@PathVariable Long userId);
}
