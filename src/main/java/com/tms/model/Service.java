package com.tms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private int id;
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "[A-z]*")
    @Column(name = "section")
    private String section;
    @Column(name = "description")
    private String description;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "user_id")
    private int userId;
    @Max(5)
    @Column(name = "rating")
    private int rating;
    @JsonManagedReference
    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER)
    private Set<Feedback> feedbackList = new LinkedHashSet<>();
}