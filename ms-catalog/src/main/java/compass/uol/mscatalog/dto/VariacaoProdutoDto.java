package compass.uol.mscatalog.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProdutoDto {

    private String id;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer quantity;
    private ProdutoInfoDto product;
}
