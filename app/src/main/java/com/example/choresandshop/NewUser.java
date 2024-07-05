package com.example.choresandshop;



public class NewUser {
    private String email;
    private String username;
    private UserRoleEnum role;
    private String avatar;


    public NewUser(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "NewUser{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", Role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}