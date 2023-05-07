package com.tms.service;

import com.tms.model.Feedback;
import com.tms.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public ArrayList<Feedback> getAllFeedbacksForService(int toWhichServiceId) {
        try {
            return feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(toWhichServiceId).orElse(new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Feedback createFeedback(Feedback feedback) {
        try {
            feedback.setCreated(new Timestamp(System.currentTimeMillis()));
            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteFeedback(int id) {
        feedbackRepository.deleteFeedback(id);
    }
}