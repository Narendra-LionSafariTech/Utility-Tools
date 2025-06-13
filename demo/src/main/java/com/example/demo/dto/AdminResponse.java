package com.example.demo.dto;

import com.example.demo.model.Admin;

public class AdminResponse {

    private String message;
    private Admin admin;

    public AdminResponse(String message, Admin admin) {
        this.message = message;
        this.admin = admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}

