package com.solvd.laba.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class User {

    private int id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String zipCode;
}
