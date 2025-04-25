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
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String videoUrl;

    @ManyToOne
    private User addedBy; // 👈 le user qui a ajouté ce film

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Box> boxes;
}
