package compass.uol.mscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {

    private String id;
    private String name;
    private Boolean active;

}
