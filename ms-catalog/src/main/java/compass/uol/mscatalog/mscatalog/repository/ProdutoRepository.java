package compass.uol.mscatalog.mscatalog.repository;

import compass.uol.mscatalog.mscatalog.entity.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface ProdutoRepository extends MongoRepository<Produto, String> {

    Optional<Produto> findByVariationsId(String id);

    //List<Produto> findAllByCategories(String id);
}
