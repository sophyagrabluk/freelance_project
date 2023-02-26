package org;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Feedback {
    private int id;
    private String comment;
    private double rating;
    private Timestamp created;
    private int toWhichUserId;
    private int fromWhichUserId;
}
