package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundException;
import com.tms.model.Feedback;
import com.tms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedbacksForService(int toWhichServiceId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(toWhichServiceId);
        if (!feedbacks.isEmpty()) {
            return feedbacks.stream().filter(feedback -> !feedback.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no feedbacks for this service");
        }
    }

    @Transactional
    public Feedback createFeedback(@Valid Feedback feedback, BindingResult bindingResult) {
        feedback.setCreated(new Timestamp(System.currentTimeMillis()));
        Feedback newFeedback = feedbackRepository.save(feedback);
        feedbackRepository.updateRating(feedback.getToWhichServiceId());
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        } else {
            return newFeedback;
        }
    }

    @Transactional
    public void deleteFeedback(int id) {
        feedbackRepository.deleteFeedback(id);
    }
}