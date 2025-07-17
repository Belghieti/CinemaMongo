package com.demo.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "boxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Box {
    @Id
    private String id;

    private String name;     // Nom de la room
    private boolean active;  // Est-ce que la session est en cours ?

    private String hostId;   // id du user host
    private String movieId;  // id du film

    private List<String> invitationIds; // ids des invitations
    private List<String> participantIds; // ids des participants
}
