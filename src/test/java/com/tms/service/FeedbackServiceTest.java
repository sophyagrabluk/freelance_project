package com.tms.service;

import com.tms.mapper.FeedbackToFeedbackResponseMapper;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackToFeedbackResponseMapper feedbackToFeedbackResponseMapper;

    private int id;

    private Feedback feedback;

    private FeedbackResponse feedbackResponse;

    private final List<Feedback> feedbacks = new ArrayList<>();

    private final List<FeedbackResponse> feedbackResponses = new ArrayList<>();
    private final Timestamp time = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init() {
        id = 1;
        feedback = new Feedback();
        feedback.setId(1);
        feedback.setComment("CommentTest");
        feedback.setRating(5);
        feedback.setToWhichServiceId(1);
        feedback.setFromWhichUserId(1);
        feedback.setCreated(time);
        feedback.setDeleted(false);
        feedbacks.add(feedback);
        feedbackResponse = new FeedbackResponse();
        feedbackResponses.add(feedbackResponse);
    }

    @Test
    public void getAllFeedbacksForServiceTest() {
        when(feedbackRepository.findAllByToWhichServiceIdOrderByCreatedDesc(id)).thenReturn(feedbacks);
        when(feedbackToFeedbackResponseMapper.feedbackResponse(feedback)).thenReturn(feedbackResponse);
        assertEquals(feedbackResponses, feedbackService.getAllFeedbacksResponseForService(id));
    }

    @Test
    public void createFeedbackTest() {
        feedback.setCreated(time);
        feedbackService.createFeedback(feedback);
        verify(feedbackRepository).updateRating(id);
        verify(feedbackRepository).save(feedback);
    }

    @Test
    public void deleteFeedback() {
        feedbackService.deleteFeedback(id);
        verify(feedbackRepository).deleteFeedback(id);
    }
}
