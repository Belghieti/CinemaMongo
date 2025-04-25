package com.demo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoSyncMessage {
    private String action; // "play", "pause", "seek"
    private double time;   // Temps actuel de la vidéo
}
