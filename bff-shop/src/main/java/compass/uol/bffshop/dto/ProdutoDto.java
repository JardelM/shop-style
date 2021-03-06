package compass.uol.bffshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {

    //private String id;
    private String name;
    private String description;
    private Boolean active;

    private List<VariacaoDto> variations = new ArrayList<>();
}