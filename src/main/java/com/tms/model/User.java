package com.tms.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Component
@Data
@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "users_table_id_seq", allocationSize = 1)
    int id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "country")
    String country;
    @Column(name = "city")
    String city;
    @Size(min = 5, max = 30)
    @Pattern(regexp = "[a-z0-9]*")
    @Column(name = "login")
    String login;
    @Column(name = "password")
    String password;
    @Column(name = "created", updatable = false)
    Timestamp created;
    @Column(name = "changed")
    Timestamp changed;
    @Column(name = "is_deleted")
    boolean isDeleted;
}