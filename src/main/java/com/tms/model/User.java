package com.tms.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Component
@Data
public class User {
    int id;
    String firstName;
    String lastName;
    String country;
    String city;
    @Size(min = 5, max = 30)
    @Pattern(regexp = "[a-z0-9]*")
    String login;
    String password;
    Timestamp created;
    Timestamp changed;
    boolean isDeleted;
}