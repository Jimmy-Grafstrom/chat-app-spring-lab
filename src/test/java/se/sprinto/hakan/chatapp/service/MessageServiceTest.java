package se.sprinto.hakan.chatapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    private Message message1;
    private Message message2;
    private Message messageToSave;
    private List<Message> testMessages;
    private User user;
    private LocalDateTime now;

    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        user = new User(1L, "username", "password");
    }

    @Test
    void save() {
//      ARRANGE
        messageToSave = new Message(user, "vi ses", now.minusMinutes(1L));

//      ACT
        messageService.save(messageToSave);

//      ASSERT
        verify(messageRepository, times(1)).save(messageToSave);
    }

    @Test
    void getMessages() {
//      ARRANGE
        message1 = new Message(user,"hej", now);
        message2 = new Message(user, "hejdå", now.minusMinutes(5L));
        testMessages = List.of(message1, message2);
        user.setMessages(testMessages);
        when(messageRepository.findByUserId(user.getId())).thenReturn(user.getMessages());

//      ACT
        List<Message> result = messageService.getMessages(1L);

//      ASSERT
        assertEquals(2, result.size());
        assertEquals("hej", result.get(0).getText());
        verify(messageRepository, times(1)).findByUserId(1L);
    }
}