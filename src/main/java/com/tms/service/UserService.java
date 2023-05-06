package com.tms.service;

import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

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
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent() && !user.get().isDeleted()) {
                return user.orElse(null);
            }
            return null;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User createUser(User user) {
        try {
            user.setCreated(new Timestamp(System.currentTimeMillis()));
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            return userRepository.save(user);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public User updateUser(User user) {
        try {
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            return userRepository.saveAndFlush(user);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
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
}