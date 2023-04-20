package com.tms.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import java.sql.Timestamp;

@Component
@Data
public class Feedback {
    private int id;
    private String comment;
    @Max(5)
    private int rating;
    private Timestamp created;
    private int toWhichUserId;
    private int fromWhichUserId;
}