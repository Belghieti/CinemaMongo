package com.demo.backend.DTOs;

import com.demo.backend.model.Invitation;

public class InvitationDTO {
    private String id;
    private String status;
    private boolean accepted;
    private String senderId;
    private String boxId;

    public InvitationDTO(Invitation invitation) {
        this.id = invitation.getId();
        this.status = invitation.getStatus();
        this.accepted = invitation.isAccepted();
        this.senderId = invitation.getSenderId();
        this.boxId = invitation.getBoxId();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getBoxId() { return boxId; }
    public void setBoxId(String boxId) { this.boxId = boxId; }
}
