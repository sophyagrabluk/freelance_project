package com.tms.controller;

import com.tms.exception.BadRequestException;
import com.tms.model.Feedback;
import com.tms.model.response.FeedbackResponse;
import com.tms.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{toWhichServiceId}")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacksResponseForService(@PathVariable int toWhichServiceId) {
        return new ResponseEntity<>(feedbackService.getAllFeedbacksResponseForService(toWhichServiceId), HttpStatus.OK);
    }

    @Operation(summary = "Create feedback for service and count common rating")
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody @Valid Feedback feedback, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            feedbackService.createFeedback(feedback);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Check your info and try again");
        }
    }

    @Operation(summary = "Update feedback's comment")
    @PutMapping
    public ResponseEntity<HttpStatus> updateFeedback(@RequestBody Feedback feedback){
        feedbackService.updateFeedback(feedback);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFeedback(@RequestParam int id) {
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Provide all all feedbacks' information (including deleted) for one service")
    @GetMapping("/admin/{toWhichServiceId}")
    public ResponseEntity<List<Feedback>> getAllFeedbacksForService(@PathVariable int toWhichServiceId) {
        return new ResponseEntity<>(feedbackService.getAllFeedbacksForService(toWhichServiceId), HttpStatus.OK);
    }

    @DeleteMapping("/admin")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@RequestParam int id) {
        feedbackService.deleteFeedbackByAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}