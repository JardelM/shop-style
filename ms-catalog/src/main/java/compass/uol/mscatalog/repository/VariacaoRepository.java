package compass.uol.mscatalog.repository;

import compass.uol.mscatalog.entity.Variacao;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface VariacaoRepository extends MongoRepository<Variacao, String > {
}
