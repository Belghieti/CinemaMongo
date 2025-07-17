package com.demo.backend.service;


import com.demo.backend.model.Box;
import com.demo.backend.model.Movie;
import com.demo.backend.model.User;
import com.demo.backend.repository.BoxRepository;
import com.demo.backend.repository.MovieRepository;
import com.demo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public Box createBoxFromUsername(String username, String movieId, String name) {
        User host = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Box box = new Box();
        box.setHostId(host.getId());
        box.setMovieId(movie.getId());
        box.setName(name);
        box.setActive(true);
        box.setParticipantIds(new java.util.ArrayList<>());
        box.setInvitationIds(new java.util.ArrayList<>());
        return boxRepository.save(box);
    }

}