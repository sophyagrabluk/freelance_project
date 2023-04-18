package com.tms.service;

import com.tms.domain.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
//    {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
        return userRepository.createUser(user);
    }
    public boolean updateUser (User user){
        return userRepository.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public boolean addServiceToUser (int userId, int serviceId){
        return userRepository.addServiceToUser(userId,serviceId);
    }
}
