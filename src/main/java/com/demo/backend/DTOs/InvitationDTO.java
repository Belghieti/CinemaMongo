package com.demo.backend.DTOs;

import com.demo.backend.model.Invitation;

public class InvitationDTO {
    private Long id;
    private String status;
    private boolean accepted;
    private UserDTO sender;
    private BoxDTO box;

    public InvitationDTO(Invitation invitation) {
        this.id = invitation.getId();
        this.status = invitation.getStatus();
        this.accepted = invitation.isAccepted();
        this.sender = new UserDTO(invitation.getSender());
        this.box = new BoxDTO(invitation.getBox());
    }
}
