package com.demo.backend.DTOs;

import com.demo.backend.model.Box;

public class BoxDTO {
    private Long id;
    private String name;

    public BoxDTO(Box box) {
        this.id = box.getId();
        this.name = box.getName();
    }
}
