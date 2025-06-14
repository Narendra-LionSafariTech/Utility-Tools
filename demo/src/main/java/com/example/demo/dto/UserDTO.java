package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.User;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String city;
    private String state;
    private String country;
    private String address;
    private String avatar;
    private Date lastLogin;
    private String deviceToken;
    private String dateOfBirth;
    private String gender;
    private String role;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String mobile, String city, String state, String country,
                   String address, String avatar, Date lastLogin, String deviceToken, String dateOfBirth,
                   String gender, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.avatar = avatar;
        this.lastLogin = lastLogin;
        this.deviceToken = deviceToken;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
    }

    // ✅ Static mapper method (place this inside the class!)
    public static UserDTO mapToDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getMobile(),
            user.getCity(),
            user.getState(),
            user.getCountry(),
            user.getAddress(),
            user.getAvatar(),
            user.getLastLogin(),
            user.getDeviceToken(),
            user.getDateOfBirth(),
            user.getGender(),
            user.getRole()
        );
    }

    // Getters and Setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

    public String getDeviceToken() { return deviceToken; }
    public void setDeviceToken(String deviceToken) { this.deviceToken = deviceToken; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
