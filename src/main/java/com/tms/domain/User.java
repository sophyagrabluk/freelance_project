package com.tms.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
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
