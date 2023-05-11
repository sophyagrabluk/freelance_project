package com.tms.model.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FeedbackResponse {

    String comment;
    double rating;
    Timestamp created;
}
