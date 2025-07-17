package com.demo.backend.repository;

import com.demo.backend.model.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoxRepository extends MongoRepository<Box, String> {
}
