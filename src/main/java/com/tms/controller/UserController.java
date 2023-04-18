package com.tms.controller;

import com.tms.domain.User;
import com.tms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public String createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return "unsuccessfully";
        }
        boolean result = userService.createUser(user);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PutMapping
    public String updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return "unsuccessfully";
        }
        boolean result = userService.updateUser(user);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam int id) {
        if (userService.deleteUser(id)) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PostMapping("/{userId}/{serviceId}")
    public String addServiceToUser(@PathVariable int userId, @PathVariable int serviceId) {
        boolean result = userService.addServiceToUser(userId, serviceId);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}