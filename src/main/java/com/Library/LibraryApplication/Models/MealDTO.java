package com.Library.LibraryApplication.Models;

import jakarta.validation.constraints.NotEmpty;

public class MealDTO {

    @NotEmpty(message = "The name is required")
    private String name;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
