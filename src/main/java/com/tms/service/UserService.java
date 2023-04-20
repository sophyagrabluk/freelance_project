package com.tms.service;

import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        try {
            return userRepository.getUserById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
        try {
            return userRepository.createUser(user);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            return userRepository.updateUser(user);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public boolean addServiceToUser(int userId, int serviceId) {
        try {
            return userRepository.addServiceToUser(userId, serviceId);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }
}
