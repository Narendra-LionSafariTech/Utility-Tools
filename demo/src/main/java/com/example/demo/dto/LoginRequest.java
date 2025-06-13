package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;
    private String deviceToken;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password, String deviceToken) {
        this.email = email;
        this.password = password;
        this.deviceToken = deviceToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
