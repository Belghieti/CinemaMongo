package com.demo.backend.DTOs;

import com.demo.backend.model.Box;

public class BoxDTO {
    private String id;
    private String name;

    public BoxDTO(Box box) {
        this.id = box.getId();
        this.name = box.getName();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
