package compass.uol.mscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoMessageDto {

    private String variant_id;
    private Integer quantity;
}
