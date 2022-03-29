package compass.uol.mscatalog.repository;

import compass.uol.mscatalog.entity.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ProdutoRepository extends MongoRepository<Produto, String> {

    Optional<Produto> findByVariationsId(String id);

    //List<Produto> findAllByCategories(String id);
}
