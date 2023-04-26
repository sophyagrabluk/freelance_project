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
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Component
@Data
@Entity
@Table(name = "services_table")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id_seq_gen")
    @SequenceGenerator(name = "service_id_seq_gen", sequenceName = "services_table_id_seq", allocationSize = 1)
    int id;
    @Column(name = "name")
    String name;
    @Pattern(regexp = "[A-z]*")
    @Column(name = "section")
    String section;
    @Column(name = "description")
    String description;
    @Column(name = "is_deleted")
    boolean isDeleted;
    @Column(name = "user_id")
    int userId;
    @Max(5)
    @Column(name = "rating")
    int rating;
}