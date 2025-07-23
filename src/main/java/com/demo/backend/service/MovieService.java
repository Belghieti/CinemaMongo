package com.demo.backend.service;


import com.demo.backend.model.Movie;
import com.demo.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> getAllMoviesByAddedById(String userId) {
        return movieRepository.findAllByAddedById(userId);
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }
}
