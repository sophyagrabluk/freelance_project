package com.tms.service;

import com.tms.exception.ForbiddenException;
import com.tms.exception.NotFoundExc;
import com.tms.exception.ObjectIsDeletedException;
import com.tms.mapper.FeedbackToFeedbackResponseMapper;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.repository.FeedbackRepository;
import com.tms.security.CheckingAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper;

    private final CheckingAuthorization checkingAuthorization;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper, CheckingAuthorization checkingAuthorization) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackToFeedbackResponseMapper = feedbackToFeedbackResponseMapper;
        this.checkingAuthorization = checkingAuthorization;
    }

    public List<FeedbackResponse> getAllFeedbacksResponseForService(int toWhichServiceId) {
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
    public void updateFeedback(Feedback feedback) {
        if (checkingAuthorization.check(getUserLogin(feedback.getId()))) {
            if (feedbackRepository.findById(feedback.getId()).isPresent()) {
                feedbackRepository.updateFeedback(feedback.getId(), feedback.getComment());
            } else {
                throw new NotFoundExc("There is no this feedback");
            }
        } else {
            throw new ForbiddenException("You can't update feedback from another user");
        }
    }

    @Transactional
    public void deleteFeedback(int id) {
        feedbackRepository.deleteFeedback(id);
    }

    public List<Feedback> getAllFeedbacksForService(int toWhichServiceId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(toWhichServiceId);
        if (!feedbacks.isEmpty()) {
            return feedbacks;
        } else {
            throw new NotFoundExc("There are no any feedbacks for this service");
        }
    }

    @Transactional
    public void deleteFeedbackByAdmin(int id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new NotFoundExc("There is no such feedback"));
        if (!feedback.isDeleted()) {
            feedback.setDeleted(true);
        } else {
            throw new ObjectIsDeletedException("Feedback is already deleted");
        }
    }

    private String getUserLogin(int id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow
                (() -> new NotFoundExc("There are no any feedbacks for this service"));
        return feedback.getFromWhichUserLogin();
    }
}