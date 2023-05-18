package com.tms.service;

import com.tms.mapper.FeedbackToFeedbackResponseMapper;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.repository.FeedbackRepository;
import com.tms.security.CheckingAuthorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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

    @Mock
    private CheckingAuthorization checkingAuthorization;

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
        feedback.setFromWhichUserLogin("LoginTest");
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
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("LoginTest");
        feedback.setCreated(time);
        feedbackService.createFeedback(feedback);
        verify(feedbackRepository).updateRating(id);
        verify(feedbackRepository).save(feedback);
    }

    @Test
    public void updateFeedbackTest() {
        when(checkingAuthorization.check(feedback.getFromWhichUserLogin())).thenReturn(true);
        when(feedbackRepository.findById(id)).thenReturn(Optional.of(feedback));
        feedbackService.updateFeedback(feedback);
        verify(feedbackRepository).updateFeedback(id, feedback.getComment());
    }

    @Test
    public void deleteFeedback() {
        feedbackService.deleteFeedback(id);
        verify(feedbackRepository).deleteFeedback(id);
    }
}
