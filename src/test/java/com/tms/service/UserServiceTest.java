package com.tms.service;

import com.tms.mapper.UserToUserResponseMapper;
import com.tms.model.User;
import com.tms.model.response.UserResponse;
import com.tms.repository.UserRepository;
import com.tms.security.CheckingAuthorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserToUserResponseMapper userToUserResponseMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private int id = 1;
    private User user;
    private UserResponse userResponse;

    @InjectMocks
    private CheckingAuthorization checkingAuthorization;

    @BeforeEach
    public void init() {
        id = 1;
        user = new User();
        userResponse = new UserResponse();
    }

    @Test
    public void getUserByIdTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        UserResponse returned = userService.getUserById(id);
        verify(userRepository).findById(id);
        verify(userToUserResponseMapper).userToResponse(user);
        assertEquals(userResponse, returned);
    }
}
