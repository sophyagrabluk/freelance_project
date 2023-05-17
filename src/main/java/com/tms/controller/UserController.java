package com.tms.controller;

import com.tms.exception.BadRequestException;
import com.tms.model.User;
import com.tms.model.request.UpdatePasswordRequest;
import com.tms.model.response.UserResponse;
import com.tms.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserResponseById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserResponseById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsersResponse() {
        return new ResponseEntity<>(userService.getAllUsersResponse(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Check your info and try again");
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new BadRequestException("Check your new info and try again");
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<HttpStatus> updateUserPassword(@RequestBody @Valid UpdatePasswordRequest request, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.updateUserPassword(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new BadRequestException("Check your new info and try again");
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add service to user's Favourite")
    @PostMapping("/addFav")
    public ResponseEntity<HttpStatus> addServiceToUser(@RequestParam int userId, @RequestParam int serviceId) {
        userService.addServiceToUser(userId, serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Remove service from user's Favourite")
    @DeleteMapping("/removeFav")
    public ResponseEntity<HttpStatus> removeServiceFromUser(@RequestParam int userId, @RequestParam int serviceId) {
        userService.removeServiceFromUser(userId, serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Provide all users' information in database (including deleted)")
    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Provide all information from one user in database")
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@RequestParam int id) {
        userService.deleteUserByAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}