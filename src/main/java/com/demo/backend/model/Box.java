package com.demo.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;     // Nom de la room
    private boolean active;  // Est-ce que la session est en cours ?

    @ManyToOne
    @JoinColumn(name="host_id")
    private User host;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "box")
    @JsonIgnore
    private List<Invitation> invitations;

    @ManyToMany
    @JoinTable(
            name = "box_participants",
            joinColumns = @JoinColumn(name = "box_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants; // 👈 les utilisateurs qui ont accepté
}
