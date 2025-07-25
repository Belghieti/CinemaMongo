package com.demo.backend.DTOs;

import lombok.Data;

@Data
public class BoxResponse {
    private String boxId;
    private String name;
    private String movieId;
    private String videoUrl;

    public BoxResponse(String id, String name, String id1, String decryptedUrl) {
        this.boxId = id;
        this.name = name;
        this.movieId = id1;
        this.videoUrl = decryptedUrl;
    }
}
