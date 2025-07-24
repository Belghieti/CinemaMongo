package com.demo.backend.service;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;


import com.demo.backend.model.User;
import com.demo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    // Anti-spam cache: email -> timestamp de dernière tentative
    // Anti-spam par IP : IP -> dernière tentative
    private static final ConcurrentHashMap<String, Instant> ipAttempts = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Instant> registrationAttempts = new ConcurrentHashMap<>();
    private static final long RATE_LIMIT_SECONDS = 60; // 60 secondes entre deux tentatives avec le même email

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(Map<String, String> body , String clientIp) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        String captcha = body.get("captcha");
        // Vérification reCAPTCHA
        if (captcha == null || !captchaService.verifyCaptcha(captcha)) {
            return ResponseEntity.status(400).body("Échec vérification reCAPTCHA");
        }
        // Blocage par IP
        Instant lastIpAttempt = ipAttempts.get(clientIp);
        if (lastIpAttempt != null && Instant.now().minusSeconds(RATE_LIMIT_SECONDS).isBefore(lastIpAttempt)) {
            return ResponseEntity.status(429).body("Too many requests from your IP. Please wait.");
        }
        // 🔐 Anti-spam check
        Instant lastAttempt = registrationAttempts.get(email);
        if (lastAttempt != null && Instant.now().minusSeconds(RATE_LIMIT_SECONDS).isBefore(lastAttempt)) {
            return ResponseEntity.status(429).body("Too many attempts. Please wait before trying again.");
        }

        // Enregistrer l'heure de la tentative
        registrationAttempts.put(email, Instant.now());

        if (userRepo.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> login(Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Optional<User> optional = userRepo.findByUsername(username);

        if (optional.isPresent() &&
                passwordEncoder.matches(password, optional.get().getPassword())) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public ResponseEntity<?> getUserInfo(Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail()
        ));
    }

    public ResponseEntity<?> getUserEmail(Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(Map.of("email", user.getEmail()));
    }
    public List<User> getAlluser(){
        return userRepo.findAll();
    }
}
