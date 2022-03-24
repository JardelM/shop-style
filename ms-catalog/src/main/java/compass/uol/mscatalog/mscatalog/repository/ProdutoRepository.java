package compass.uol.mscatalog.mscatalog.repository;

import compass.uol.mscatalog.mscatalog.entity.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ProdutoRepository extends MongoRepository<Produto, String> {

    //List<Produto> findAllByCategories(String id);
}
