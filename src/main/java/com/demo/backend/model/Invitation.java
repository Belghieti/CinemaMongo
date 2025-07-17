package com.demo.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "invitations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
    @Id
    private String id;
    private String status; // "pending", "accepted", "declined"
    private boolean accepted;
    private String senderId;
    private String receiverId;
    private String boxId;
}
