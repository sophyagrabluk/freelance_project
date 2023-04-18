package com.tms.service;

import com.tms.domain.Feedback;
import com.tms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FeedbackService {
//    {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public ArrayList<Feedback> getAllFeedback(int toWhichUserId) {
       return feedbackRepository.getAllFeedback(toWhichUserId);
    }

    public boolean createFeedback(Feedback feedback) {
       return feedbackRepository.createFeedback(feedback);
    }
}