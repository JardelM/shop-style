package compass.uol.mscatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Produto {

    @Id
    private String id;
    private String name;
    private String description;
    private Boolean active;

    @DBRef
    private List<Variacao> variations = new ArrayList<>();


}
