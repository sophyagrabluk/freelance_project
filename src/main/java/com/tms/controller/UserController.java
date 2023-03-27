package com.tms.controller;

import com.tms.domain.User;
import com.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public String createUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String login,
            @RequestParam String password
    ) {
        boolean result = userService.createUser(firstName, lastName, country, city, login, password);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PutMapping
    public String updateUser(
            @RequestParam String id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String login,
            @RequestParam String password
    ) {
        boolean result = userService.updateUser(Integer.parseInt(id), firstName, lastName, country, city, login, password);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

        @DeleteMapping("/{id}")
        public String deleteUser(@PathVariable int id) {
            if (userService.deleteUser(id)) {
                return "successfully";
            }
            return "unsuccessfully";
        }
}
