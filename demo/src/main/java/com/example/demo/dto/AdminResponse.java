package com.example.demo.dto;

public class AdminResponse {

    private String message;
    private AdminDTO admin;
    private String token;

    public AdminResponse(String message, AdminDTO admin) {
        this.message = message;
        this.admin = admin;
        this.token = null;
    }

    public AdminResponse(String message, AdminDTO admin, String token) {
        this.message = message;
        this.admin = admin;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public AdminDTO getAdmin() {
        return admin;
    }

    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAdmin(AdminDTO admin) {
        this.admin = admin;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
