package com.tms.controller;

import com.tms.domain.Feedback;
import com.tms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

//    @GetMapping("/{toWhichUserId}")
//    public String getAllFeedbacksForService(@PathVariable int toWhichUserId, Model model) {
//        Feedback feedback = feedbackService.getAllFeedback(toWhichUserId);
//        model.addAttribute("toWhichUserId", toWhichUserId);
//        return "allFeedbacksForUser";
//    }

    @PostMapping
    public String createFeedback(
            @RequestParam String rating,
            @RequestParam String comment,
            @RequestParam String toWhichServiceId,
            @RequestParam String fromWhichUserId
            ) {
        boolean result = feedbackService.createFeedback(Double.parseDouble(rating), comment, Integer.parseInt(toWhichServiceId), Integer.parseInt(fromWhichUserId));
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}
