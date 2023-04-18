package com.tms.controller;

import com.tms.domain.Feedback;
import com.tms.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ArrayList<Feedback> getAllFeedbacksForService(@PathVariable int toWhichUserId) {
        return feedbackService.getAllFeedback(toWhichUserId);
    }

    @PostMapping
    public String createFeedback(@RequestBody @Valid Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return "unsuccessfully";
        }
        boolean result = feedbackService.createFeedback(feedback);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}
