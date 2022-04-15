package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoDto {

    private UsuarioDto user;
    private List<CompraDto> purchases;
}
