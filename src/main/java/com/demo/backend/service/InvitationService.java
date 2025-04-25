package com.demo.backend.service;

import com.demo.backend.model.Box;
import com.demo.backend.model.Invitation;
import com.demo.backend.model.User;
import com.demo.backend.repository.BoxRepository;
import com.demo.backend.repository.InvitationRepository;
import com.demo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final BoxRepository boxRepository;

    public Invitation sendInvitation(Long senderId, Long receiverId, Long boxId) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();
        Box box = boxRepository.findById(boxId).orElseThrow();

        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setBox(box);
        invitation.setStatus("pending");
        invitation.setAccepted(false);

        return invitationRepository.save(invitation);
    }

    public Invitation acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow();

        invitation.setAccepted(true);
        invitation.setStatus("accepted");

        // Ajouter le receiver à la liste des participants de la box
        Box box = invitation.getBox();
        box.getParticipants().add(invitation.getReceiver());
        boxRepository.save(box);

        return invitationRepository.save(invitation);
    }
}