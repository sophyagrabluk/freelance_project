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
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Size(min = 5, max = 30)
    @Pattern(regexp = "[a-z0-9]*")
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "created", updatable = false)
    private Timestamp created;
    @Column(name = "changed")
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}