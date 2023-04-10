package com.tms.controller;

import com.tms.domain.User;
import com.tms.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = Logger.getLogger(this.getClass());

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "singleUser";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        ArrayList<User> users = userService.getAllUsers();
        model.addAttribute("user", users);
        return "singleUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            logger.warn("Oops, error with validation");
            return "unsuccessfully";
        }
        boolean result = userService.createUser(user);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PutMapping
    public String updateUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            logger.warn("Oops, error with validation");
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