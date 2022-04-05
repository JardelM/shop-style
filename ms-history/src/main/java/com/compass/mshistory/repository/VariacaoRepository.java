package com.compass.mshistory.repository;

import com.compass.mshistory.entity.Variacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariacaoRepository extends MongoRepository <Variacao, String> {
}
