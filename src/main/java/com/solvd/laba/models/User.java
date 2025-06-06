package com.solvd.laba.models;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String zipCode;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
