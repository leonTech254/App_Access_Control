package com.example.appmanagement;

public class UserItems {



    private String username;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    private String  UserId;


    public UserItems(String username, String email, String userId) {
        this.username = username;
        this.email = email;
        UserId = userId;
    }
}
