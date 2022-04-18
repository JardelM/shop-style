package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoInfoDto {

    private String name;
    private String description;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer quantity;
}
