package com.tms;

import com.tms.mapper.UserToUserResponseMapper;
import com.tms.model.User;
import com.tms.model.request.UpdatePasswordRequest;
import com.tms.model.response.UserResponse;
import com.tms.repository.UserRepository;
import com.tms.security.CheckingAuthorization;
import com.tms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserToUserResponseMapper userToUserResponseMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CheckingAuthorization checkingAuthorization;

    private UpdatePasswordRequest updatePasswordRequest;

    private int id;

    private User user;

    private UserResponse userResponse;

    private final List<User> users = new ArrayList<>();

    private final List<UserResponse> userResponses = new ArrayList<>();
    private final Timestamp time = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init() {
        id = 1;
        user = new User();
        user.setId(1);
        user.setFirstName("FirstNameTest");
        user.setLastName("LastNAmeTest");
        user.setCity("CityTest");
        user.setLogin("LoginTest");
        user.setPassword("PasswordTest");
        user.setEmail("test@test.test");
        user.setRole("USER");
        user.setCreated(time);
        user.setChanged(time);
        user.setDeleted(false);
        users.add(user);
        userResponse = new UserResponse();
        userResponses.add(userResponse);
        updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setId(1);
        updatePasswordRequest.setOldPassword("PasswordTest");
        updatePasswordRequest.setNewPassword("NewPasswordTest");
    }

    @Test
    public void getUserResponseByIdTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        UserResponse returned = userService.getUserResponseById(id);
        verify(userRepository).findById(id);
        verify(userToUserResponseMapper).userToResponse(user);
        assertEquals(userResponse, returned);
    }

    @Test
    public void getAllUsersResponseTest() {
        when(userRepository.findAll()).thenReturn(users);
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        assertEquals(userResponses, userService.getAllUsersResponse());
    }

    @Test
    public void createUserTest() {
        user.setCreated(time);
        user.setChanged(time);
        user.setRole("USER");
        when(passwordEncoder.encode("PasswordTest")).thenReturn("encodedPassword");
        userService.createUser(user);
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void updateUserTest() {
        when(checkingAuthorization.check(user.getLogin())).thenReturn(true);
        user.setChanged(time);
        userService.updateUser(user);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void updateUserPasswordTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(updatePasswordRequest.getNewPassword())).thenReturn("NewPasswordTest");
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        userService.updateUserPassword(updatePasswordRequest);
        verify(userRepository).updateUserPassword(updatePasswordRequest.getId(), "NewPasswordTest");

    }

    @Test
    public void deleteUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(checkingAuthorization.check(user.getLogin())).thenReturn(true);
        userService.deleteUser(id);
        verify(userRepository).deleteUser(id);
    }

    @Test
    public void addServiceToUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(checkingAuthorization.check(user.getLogin())).thenReturn(true);
        userService.addServiceToUser(id, id);
        verify(userRepository).addServiceToUser(id, id);
    }

    @Test
    public void removeServiceFromUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(checkingAuthorization.check(user.getLogin())).thenReturn(true);
        userService.removeServiceFromUser(id, id);
        verify(userRepository).removeServiceFromUser(id, id);
    }
}