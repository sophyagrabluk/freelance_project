package com.tms.service;

import com.tms.model.Feedback;
import com.tms.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public ArrayList<Feedback> getAllFeedback(int toWhichUserId) {
       return feedbackRepository.getAllFeedback(toWhichUserId);
    }

    public boolean createFeedback(Feedback feedback) {
        try {
            return feedbackRepository.createFeedback(feedback);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFeedback(int id) {
        return feedbackRepository.deleteFeedback(id);
    }
}