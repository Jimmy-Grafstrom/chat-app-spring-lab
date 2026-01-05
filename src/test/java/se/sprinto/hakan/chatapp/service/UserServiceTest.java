package se.sprinto.hakan.chatapp.service;

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
private User user;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void login() {
        user = new User(1L, "username", "password");

        when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        User loggedInUser = userService.login(user.getUsername(), user.getPassword());
        assertNotNull(loggedInUser);
        assertEquals(user.getId(), loggedInUser.getId(), "id på user och inloggad user borde matcha");
        verify(userRepository, times(1)).findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Test
    void userNotFound() {
        String username = "non existing username";
        String password = "non existing password";
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(null);
        User nonExistingUser = userService.login(username, password);
        assertNull(nonExistingUser);
        verify(userRepository, times(1)).findByUsernameAndPassword(username, password);

    }

    @Test
    void register() {
        User userToSave = new User("username", "password");
        User savedUser = new User(1L, "username", "password");
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        User result = userService.register(userToSave);
        assertNotNull(result);
        assertEquals("username", result.getUsername());
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(userToSave);
    }
}