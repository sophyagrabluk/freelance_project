package org;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    int id;
    String firstName;
    String lastName;
    String country;
    String city;
    String login;
    String password;
    Timestamp created;
    Timestamp changed;
    boolean isDeleted;
    double rating;
}
