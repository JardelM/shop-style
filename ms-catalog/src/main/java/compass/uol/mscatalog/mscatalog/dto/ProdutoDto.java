package compass.uol.mscatalog.mscatalog.dto;

import compass.uol.mscatalog.mscatalog.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {

    private String id;
    private String name;
    private String description;
    private Boolean active;

    private List<VariacaoDto> variations = new ArrayList<>();
}
