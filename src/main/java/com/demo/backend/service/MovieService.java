package com.demo.backend.service;


import com.demo.backend.model.Movie;
import com.demo.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.backend.service.CryptoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    @Autowired
    private final CryptoService cryptoService;


    public Movie addMovie(Movie movie) {
        try {
            String encryptedUrl = cryptoService.encrypt(movie.getVideoUrl());
            movie.setVideoUrl(encryptedUrl);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de chiffrement de l’URL", e);
        }
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> getAllMoviesByAddedById(String userId) {
        List<Movie> movies= movieRepository.findAllByAddedById(userId);
        for (Movie movie : movies) {
            try {
                String decryptedUrl = cryptoService.decrypt(movie.getVideoUrl());
                movie.setVideoUrl(decryptedUrl);
            } catch (Exception e) {
                throw new RuntimeException("Erreur de déchiffrement de l’URL", e);
            }
        }
        return movies;
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }
}
