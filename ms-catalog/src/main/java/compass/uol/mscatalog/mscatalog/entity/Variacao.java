package compass.uol.mscatalog.mscatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Variacao {

    @Id
    private String id;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer quantity;
}
