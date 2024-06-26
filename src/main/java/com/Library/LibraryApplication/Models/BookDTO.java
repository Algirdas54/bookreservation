package com.Library.LibraryApplication.Models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class BookDTO {
    @NotEmpty(message = "The name is required")
    private String name;
    @NotEmpty(message = "The author is required")
    private String author;
    @NotEmpty(message = "The category is required")
    private String category;
    private boolean reserved;

    @Size(min = 10, message = "The Description should be at least 10 characters")
    @Size(max = 250, message = "The Description should not exceed 250 characters")
    private String description;
    private MultipartFile imageFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
