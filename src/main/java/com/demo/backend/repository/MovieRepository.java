package com.demo.backend.repository;

import com.demo.backend.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findById(String movieId);
}
