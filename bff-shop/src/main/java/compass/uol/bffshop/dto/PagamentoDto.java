package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto {

    //private Long id;
    private String type;
    private BigDecimal discount;
    private Boolean status;
}
