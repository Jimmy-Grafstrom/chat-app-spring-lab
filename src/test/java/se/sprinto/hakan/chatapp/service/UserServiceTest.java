package se.sprinto.hakan.chatapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private User existingUser;
    private User nonExistingUser;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        existingUser = new User(1L, "username", "password");
        nonExistingUser = new User("nonExisting", "nonExisting");
    }

    @Test
    void login() {
//      ARRANGE
        when(userRepository.findByUsernameAndPassword(existingUser.getUsername(), existingUser.getPassword())).thenReturn(existingUser);

//      ACT
        User loggedInUser = userService.login(existingUser.getUsername(), existingUser.getPassword());

//      ASSERT
        assertNotNull(loggedInUser);
        assertEquals(existingUser.getId(), loggedInUser.getId(), "id på user och inloggad user borde matcha");
        verify(userRepository, times(1)).findByUsernameAndPassword(existingUser.getUsername(), existingUser.getPassword());
    }

    @Test
    void userNotFound() {
//      ARRANGE
        when(userRepository.findByUsernameAndPassword(nonExistingUser.getUsername(), nonExistingUser.getPassword())).thenReturn(null);

//      ACT
        User result = userService.login(nonExistingUser.getUsername(), nonExistingUser.getPassword());

//      ASSERT
        assertNull(result);
        verify(userRepository, times(1)).findByUsernameAndPassword(nonExistingUser.getUsername(), nonExistingUser.getPassword());

    }

    @Test
    void register() {
//      ARRANGE
        User savedUser = new User(10L, nonExistingUser.getUsername(), nonExistingUser.getPassword());
        when(userRepository.save(nonExistingUser)).thenReturn(savedUser);
//
//      ACT
        User result = userService.register(nonExistingUser);

//      ASSERT
        assertNotNull(result);
        assertEquals("nonExisting", result.getUsername());
        assertEquals(10L, result.getId());
        verify(userRepository, times(1)).save(nonExistingUser);
    }
}