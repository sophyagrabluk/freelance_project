package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundExc;
import com.tms.mapper.FeedbackToFeedbackResponseMapper;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.repository.FeedbackRepository;
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
    FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper;

    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackToFeedbackResponseMapper = feedbackToFeedbackResponseMapper;
    }

    public List<FeedbackResponse> getAllFeedbacksForService(int toWhichServiceId) {
        List<FeedbackResponse> feedbacks = feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(toWhichServiceId)
                .stream().filter(feedback -> !feedback.isDeleted())
                .map(feedback -> feedbackToFeedbackResponseMapper.feedbackResponse(feedback))
                .collect(Collectors.toList());
        if (!feedbacks.isEmpty()) {
            return feedbacks;
        } else {
            throw new NotFoundExc("There are no feedbacks for this service");
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