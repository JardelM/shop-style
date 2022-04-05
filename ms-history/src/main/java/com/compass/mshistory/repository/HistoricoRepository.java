package com.compass.mshistory.repository;

import com.compass.mshistory.entity.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HistoricoRepository extends MongoRepository<Historico , String> {

    Optional<Historico> findByUserId(Long userId);

}
