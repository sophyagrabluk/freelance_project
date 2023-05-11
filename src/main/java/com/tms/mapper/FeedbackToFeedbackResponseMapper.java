package com.tms.mapper;

import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import org.springframework.stereotype.Component;

@Component
public class FeedbackToFeedbackResponseMapper {

    public FeedbackResponse feedbackResponse(Feedback feedback){
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setComment(feedback.getComment());
        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setCreated(feedback.getCreated());
        return feedbackResponse;
    }
}
