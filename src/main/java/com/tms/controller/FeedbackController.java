package com.tms.controller;

import com.tms.domain.Feedback;
import com.tms.service.FeedbackService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{toWhichUserId}")
    public String getAllFeedbacksForService(@PathVariable int toWhichUserId, Model model) {
        ArrayList<Feedback> feedback = feedbackService.getAllFeedback(toWhichUserId);
        model.addAttribute("toWhichUserId", toWhichUserId);
        return "allFeedbacksForUser";
    }

    @PostMapping
    public String createFeedback(@ModelAttribute @Valid Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            logger.warn("Oops, error with validation");
            return "unsuccessfully";
        }
        boolean result = feedbackService.createFeedback(feedback);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}
