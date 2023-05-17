package com.tms.model.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FeedbackResponse {

    private String comment;
    private double rating;
    private Timestamp created;
}