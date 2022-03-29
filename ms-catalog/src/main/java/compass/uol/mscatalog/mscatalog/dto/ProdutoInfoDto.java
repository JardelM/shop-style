package compass.uol.mscatalog.mscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoInfoDto {

    private String id;
    private String name;
    private String description;
    private Boolean active;
}
