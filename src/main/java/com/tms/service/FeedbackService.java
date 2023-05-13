package com.tms.service;

import com.tms.exception.NotFoundExc;
import com.tms.mapper.FeedbackToFeedbackResponseMapper;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper;

    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackToFeedbackResponseMapper = feedbackToFeedbackResponseMapper;
    }

    public List<FeedbackResponse> getAllFeedbacksForService(int toWhichServiceId) {
        List<FeedbackResponse> feedbacks = feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(toWhichServiceId)
                .stream().filter(feedback -> !feedback.isDeleted())
                .map(feedbackToFeedbackResponseMapper::feedbackResponse)
                .collect(Collectors.toList());
        if (!feedbacks.isEmpty()) {
            return feedbacks;
        } else {
            throw new NotFoundExc("There are no feedbacks for this service");
        }
    }

    @Transactional
    public void createFeedback(Feedback feedback) {
        feedback.setCreated(new Timestamp(System.currentTimeMillis()));
        feedbackRepository.updateRating(feedback.getToWhichServiceId());
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(int id) {
        feedbackRepository.deleteFeedback(id);
    }
}