package com.demo.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String id;
    private String title;
    private String description;
    private String videoUrl;
    private String addedById; // id du user qui a ajouté ce film
    private List<String> boxIds; // ids des box associées
}
