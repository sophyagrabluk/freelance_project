package com.tms.model;

import com.tms.utils.SectionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Component
@Data
@Entity
@Table(name = "services_table")
@ToString(exclude = {"feedbackList"})
@EqualsAndHashCode(exclude = {"feedbackList"})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id_seq_gen")
    @SequenceGenerator(name = "service_id_seq_gen", sequenceName = "services_table_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "section")
    private SectionType section;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "user_id")
    private int userId;

    @Max(5)
    @Column(name = "rating")
    private double rating;
}