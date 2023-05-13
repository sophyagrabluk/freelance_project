package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.ForbiddenException;
import com.tms.exception.NotFoundExc;
import com.tms.mapper.UserToUserResponseMapper;
import com.tms.model.User;
import com.tms.model.request.UpdatePasswordRequest;
import com.tms.model.response.UserResponse;
import com.tms.repository.UserRepository;
import com.tms.security.CheckingAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserToUserResponseMapper userToUserResponseMapper;
    private final CheckingAuthorization checkingAuthorization;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserToUserResponseMapper userToUserResponseMapper, CheckingAuthorization checkingAuthorization, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userToUserResponseMapper = userToUserResponseMapper;
        this.checkingAuthorization = checkingAuthorization;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUserById(int id) {
        return userToUserResponseMapper.userToResponse(getUserFromDb(id));
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .map(userToUserResponseMapper::userToResponse)
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NotFoundExc("There are no users");
        }
    }

    public void createUser(User user) {
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        if (checkingAuthorization.check(user.getLogin())) {
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            userRepository.saveAndFlush(user);
        } else {
            throw new ForbiddenException("You can't update another user information");
        }
    }

    @Transactional
    public void updateUserPassword(UpdatePasswordRequest request) {
        User user = getUserFromDb(request.getId());
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            String codePass = passwordEncoder.encode(request.getNewPassword());
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            userRepository.updateUserPassword(request.getId(), codePass);
        } else {
            throw new BadRequestException("Check your old and new password and try again");
        }
    }

    @Transactional
    public void deleteUser(int id) {
        if (checkingAuthorization.check(getUserFromDb(id).getLogin())) {
            userRepository.deleteUser(id);
        } else {
            throw new ForbiddenException("You can't delete another user");
        }
    }

    @Transactional
    public void addServiceToUser(int userId, int serviceId) {
        if (checkingAuthorization.check(getUserFromDb(userId).getLogin())) {
            userRepository.addServiceToUser(userId, serviceId);
        } else {
            throw new ForbiddenException("You can't add service to another user");
        }
    }

    @Transactional
    public void removeServiceFromUser(int userId, int serviceId) {
        if (checkingAuthorization.check(getUserFromDb(userId).getLogin())) {
            userRepository.removeServiceFromUser(userId, serviceId);
        } else {
            throw new ForbiddenException("You can't delete service from another user");
        }
    }

    private User getUserFromDb(int id) {
        return userRepository.findById(id).filter(user -> !user.isDeleted())
                .orElseThrow(() -> new NotFoundExc("There is no such user"));
    }
}