package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundException;
import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && !user.get().isDeleted()) {
            return user.orElse(null);
        } else {
            throw new NotFoundException("There is no such user");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users.stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no users");
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