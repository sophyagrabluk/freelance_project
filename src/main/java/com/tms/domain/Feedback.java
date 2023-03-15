package com.tms.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class Feedback {
    private int id;
    private String comment;
    private double rating;
    private Timestamp created;
    private int toWhichUserId;
    private int fromWhichUserId;
}
