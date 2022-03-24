package compass.uol.mscatalog.mscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaFormDto {

    @NotBlank (message = "Campo obrigatório")
    private String name;
    @NotNull
    private Boolean active;
}
