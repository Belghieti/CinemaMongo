package com.demo.backend.controller;

import com.demo.backend.model.Box;
import com.demo.backend.model.Movie;
import com.demo.backend.model.User;
import com.demo.backend.repository.BoxRepository;
import com.demo.backend.repository.MovieRepository;
import com.demo.backend.repository.UserRepository;
import com.demo.backend.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final BoxRepository boxRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @PostMapping("/create")
    public Box createBox(@RequestParam String movieId,
                         @RequestParam String name,
                         Authentication authentication) {
        String username = authentication.getName();
        return boxService.createBoxFromUsername(username, movieId, name);
    }

    @GetMapping("/{boxId}")
    public ResponseEntity<?> getBoxDetails(@PathVariable String boxId, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body("Utilisateur non trouvé");
        }
        User user = optionalUser.get();
        String userId = user.getId();
        Optional<Box> optionalBox = boxRepository.findById(boxId);
        if (optionalBox.isEmpty()) {
            return ResponseEntity.status(404).body("Box non trouvée");
        }
        Box box = optionalBox.get();
        // Ajout du film associé
        Movie movie = null;
        if (box.getMovieId() != null) {
            Optional<Movie> optionalMovie = movieRepository.findById(box.getMovieId());
            if (optionalMovie.isPresent()) {
                movie = optionalMovie.get();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("box", box);
        response.put("movie", movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boxId}/join")
    public ResponseEntity<Map<String, Object>> joinBox(@RequestParam String userId, @PathVariable String boxId) {
        Box box = boxRepository.findById(boxId).orElseThrow();
        boolean isParticipant = box.getParticipantIds().contains(userId);
        if (!isParticipant) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Access denied. You are not invited.");
            return ResponseEntity.status(403).body(response);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Access granted to box " + boxId);
        response.put("boxId", boxId);
        response.put("userId", userId);
        return ResponseEntity.ok(response);
    }


}