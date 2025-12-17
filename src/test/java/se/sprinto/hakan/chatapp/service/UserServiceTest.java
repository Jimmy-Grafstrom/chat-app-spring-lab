package se.sprinto.hakan.chatapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
private User user;
    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User();

    }

    @Test
    void login() {
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        userService.login(user.getUsername(), user.getPassword());
        assertNotNull(user.getId());
        verify(userRepository).findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Test
    void userNotFound() {
        when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(null);
        userService.login(user.getUsername(), user.getPassword());
        assertNull(user.getId());
        System.out.println(user.getId());
        verify(userRepository).findByUsernameAndPassword(user.getUsername(), user.getPassword());

    }

    @Test
    void register() {

    }
}