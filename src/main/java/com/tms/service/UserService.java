package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundExc;
import com.tms.mapper.UserToUserResponseMapper;
import com.tms.model.User;
import com.tms.model.response.UserResponse;
import com.tms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;
    UserToUserResponseMapper userToUserResponseMapper;

    public UserService(UserRepository userRepository, UserToUserResponseMapper userToUserResponseMapper) {
        this.userRepository = userRepository;
        this.userToUserResponseMapper = userToUserResponseMapper;
    }

    public UserResponse getUserById(int id) {
        Optional<User> selectedUser = userRepository.findById(id);
        if (selectedUser.isPresent() && !selectedUser.get().isDeleted()) {
            return userToUserResponseMapper.userToResponse(selectedUser.get());
        } else {
            throw new NotFoundExc("There is no such user");
        }
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .map(user -> userToUserResponseMapper.userToResponse(user))
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NotFoundExc("There are no users");
        }
    }

    public User createUser(@Valid User user, BindingResult bindingResult) {
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        User newUser = userRepository.save(user);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        } else {
            return newUser;
        }
    }


    public User updateUser(@Valid User user, BindingResult bindingResult) {
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        User updateUser = userRepository.saveAndFlush(user);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        } else {
            return updateUser;
        }
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Transactional
    public void addServiceToUser(int userId, int serviceId) {
        userRepository.addServiceToUser(userId, serviceId);
    }

    @Transactional
    public void removeServiceFromUser(int userId, int serviceId) {
        userRepository.removeServiceFromUser(userId, serviceId);
    }
}