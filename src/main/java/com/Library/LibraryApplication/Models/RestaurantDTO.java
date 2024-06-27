package com.Library.LibraryApplication.Models;

import jakarta.validation.constraints.NotEmpty;

public class RestaurantDTO {
    @NotEmpty(message = "The name is required")
    private String name;
    @NotEmpty(message = "The code is required")
    private String code;
    @NotEmpty(message = "The address is required")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
