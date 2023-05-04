package com.tms.controller;

import com.tms.model.Feedback;
import com.tms.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;


@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{toWhichUserId}")
    public ResponseEntity<ArrayList<Feedback>> getAllFeedbacksForService(@PathVariable int toWhichUserId) {
        ArrayList<Feedback> allFeedbacks = feedbackService.getAllFeedback(toWhichUserId);
        if (allFeedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allFeedbacks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody @Valid Feedback feedback, BindingResult bindingResult) {
        Feedback resultFeedback = feedbackService.createFeedback(feedback);
        if (bindingResult.hasErrors() || resultFeedback == null) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFeedback(@RequestParam int id) {
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
