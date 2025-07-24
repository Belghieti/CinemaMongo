package com.demo.backend.repository;

import com.demo.backend.model.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoxRepository extends MongoRepository<Box, String> {
Optional<Box> findById(String boxId);
}
