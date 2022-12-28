package com.genome.parpalak.dao.model;

import java.io.Serializable;

public class User implements Serializable {

    private String password;
    private String username;
    private String email;
    private String name;

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    @Override
    public String toString() {
        return "User {" +
                "name='" + name + '\'' +
                "username='" + username + '\'' +
                "email='" + email + '\'' +
                '}';
    }

}
