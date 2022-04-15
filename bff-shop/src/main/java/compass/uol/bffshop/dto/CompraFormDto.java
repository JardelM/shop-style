package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraFormDto {

    private Long user_id;
    private Long payment_id;
    private List<CarrinhoFormDto> cart;
}
