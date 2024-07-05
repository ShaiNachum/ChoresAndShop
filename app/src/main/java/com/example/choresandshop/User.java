package com.example.choresandshop;


public class User {
    private UserId userId;
    private String username;
    private String avatar;
    private UserRoleEnum role;

    public User() {
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId id) {
        this.userId = id;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "UserBoundary [id=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar + "]";
    }
}
