package com.tms.mapper;

import com.tms.model.User;
import com.tms.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapper {

    public UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCity(user.getCity());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}