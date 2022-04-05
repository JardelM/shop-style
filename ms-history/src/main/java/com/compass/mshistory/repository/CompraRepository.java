package com.compass.mshistory.repository;

import com.compass.mshistory.entity.Compra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompraRepository extends MongoRepository<Compra , String> {
}
