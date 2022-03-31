package compass.uol.mscatalog.repository;


import compass.uol.mscatalog.entity.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoriaRepository extends MongoRepository <Categoria, String> {

    List<Categoria> findAllByProductsId(String id);

}
