package compass.uol.mscatalog.mscatalog.repository;

import compass.uol.mscatalog.mscatalog.entity.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoriaRepository extends MongoRepository<Categoria, String> {
}
