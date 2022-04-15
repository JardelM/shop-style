package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoFormDto {

    private String variant_id;
    private Integer quantity;
}
