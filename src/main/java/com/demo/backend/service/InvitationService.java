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

    public Invitation sendInvitation(String senderId, String receiverId, String boxId) {
        Invitation invitation = new Invitation();
        invitation.setSenderId(senderId);
        invitation.setReceiverId(receiverId);
        invitation.setBoxId(boxId);
        invitation.setStatus("pending");
        invitation.setAccepted(false);
        return invitationRepository.save(invitation);
    }

    public Invitation acceptInvitation(String invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow();
        invitation.setAccepted(true);
        invitation.setStatus("accepted");
        // Ajouter le receiver à la liste des participants de la box
        Box box = boxRepository.findById(invitation.getBoxId()).orElseThrow();
        box.getParticipantIds().add(invitation.getReceiverId());
        boxRepository.save(box);

        return invitationRepository.save(invitation);
    }
}