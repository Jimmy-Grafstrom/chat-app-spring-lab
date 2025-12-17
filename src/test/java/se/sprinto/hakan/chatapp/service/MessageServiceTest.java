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
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        message1 = new Message(user,"hej", LocalDateTime.now());
        message2 = new Message(user, "hejdå", LocalDateTime.now().minusMinutes(5L));
        testMessages = List.of(message1, message2);

        user.setMessages(testMessages);


    }

    @Test
    void save() {
        messageToSave = new Message(user, "vi ses", LocalDateTime.now().minusMinutes(1L));
        messageService.save(messageToSave);
        verify(messageRepository, times(1)).save(messageToSave);
    }

    @Test
    void getMessages() {
        String textOfMessage1 = "hej";
        when(messageRepository.findByUserId(user.getId())).thenReturn(user.getMessages());
        List<Message> result = messageService.getMessages(1L);
        assertEquals(2, result.size());
        assertEquals(textOfMessage1, result.get(0).getText());
        verify(messageRepository, times(1)).findByUserId(1L);
    }
}