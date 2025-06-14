package com.example.demo.dto;

public class UserResponse {
    private String message;
    private UserDTO user;
    private String token;

    public UserResponse(String message, UserDTO user) {
        this.message = message;
        this.user = user;
    }

    public UserResponse(String message, UserDTO user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}