package com.example.demo.dto;

import com.example.demo.model.Admin;

public class AdminDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String deviceToken;

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.role = admin.getRole();
        this.deviceToken = admin.getDeviceToken();
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getDeviceToken() { return deviceToken; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setDeviceToken(String deviceToken) { this.deviceToken = deviceToken; }
}
