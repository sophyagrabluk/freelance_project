package com.tms.controller;

import com.tms.model.User;
import com.tms.service.UserService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        boolean result = userService.createUser(user);
        if (bindingResult.hasErrors() || !result) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        boolean result = userService.updateUser(user);
        if (bindingResult.hasErrors() || !result) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{userId}/{serviceId}")
    public ResponseEntity<HttpStatus> addServiceToUser(@PathVariable int userId, @PathVariable int serviceId) {
        boolean result = userService.addServiceToUser(userId, serviceId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}