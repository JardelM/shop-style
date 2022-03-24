package compass.uol.mscatalog.mscatalog.repository;

import compass.uol.mscatalog.mscatalog.entity.Variacao;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface VariacaoRepository extends MongoRepository<Variacao, String > {
}
