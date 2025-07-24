package com.demo.backend.controller;

import com.demo.backend.DTOs.UserDTO;
import com.demo.backend.model.User;
import com.demo.backend.service.AuthService;
import com.demo.backend.service.JwtService;
import com.demo.backend.service.TokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body , HttpServletRequest request) {

        log.info("Registering user with body: {}", body);
        String clientIp = request.getRemoteAddr();
        return authService.register(body, clientIp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        return authService.login(body);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(Authentication auth) {
        return authService.getUserInfo(auth);
    }

    @GetMapping("/getUserInfoOneChamps")
    public ResponseEntity<?> getUserEmail(Authentication auth) {
        return authService.getUserEmail(auth);
    }
    /*@GetMapping("/getAllUsers")
    public List<User> getAllUser() {
        return  authService.getAlluser();
    }*/
    @GetMapping("/getBoxUsers/{boxId}")
    public ResponseEntity<List<UserDTO>> getBoxUsers(@PathVariable String boxId) {
        List<UserDTO> users = authService.getUsersByBox(boxId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token manquant");
        }

        String token = authHeader.substring(7);

        // Extraire la date d'expiration du token (en ms)
        long expirationTimestamp = jwtService.extractExpiration(token).getTime();

        // Ajouter à la blacklist avec la date d'expiration
        tokenBlacklist.add(token, expirationTimestamp);

        return ResponseEntity.ok("Déconnexion réussie");
    }

}
