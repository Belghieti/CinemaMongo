package com.demo.backend.controller;

import com.demo.backend.model.Invitation;
import com.demo.backend.model.User;
import com.demo.backend.repository.InvitationRepository;
import com.demo.backend.repository.UserRepository;
import com.demo.backend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    @PostMapping("/send")
    public Invitation sendInvitation(@RequestParam Long senderId,
                                     @RequestParam Long receiverId,
                                     @RequestParam Long boxId) {
        return invitationService.sendInvitation(senderId, receiverId, boxId);
    }

    @PostMapping("/{id}/accept")
    public Invitation acceptInvitation(@PathVariable Long id) {
        return invitationService.acceptInvitation(id);
    }
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedInvitations(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("Utilisateur non trouvé");
        }

        List<Invitation> receivedInvitations = invitationRepository.findByReceiver(user);

        return ResponseEntity.ok(receivedInvitations);
    }
}