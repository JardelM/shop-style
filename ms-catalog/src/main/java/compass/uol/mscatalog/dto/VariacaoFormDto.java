package compass.uol.mscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoFormDto {

    @NotBlank
    private String color;
    @NotBlank
    private String size;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;
    @NotBlank
    private String product_id;

}
