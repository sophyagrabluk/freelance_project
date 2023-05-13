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
import java.sql.Timestamp;

@Component
@Data
@Entity
@Table(name = "feedback_table")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_id_seq_gen")
    @SequenceGenerator(name = "feedback_id_seq_gen", sequenceName = "feedback_table_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Max(5)
    @Column(name = "rating")
    private int rating;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "from_which_user_id")
    private int fromWhichUserId;

    @Column(name = "to_which_service_id")
    private int toWhichServiceId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}