package se.sprinto.hakan.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.repository.MessageRepository;
import se.sprinto.hakan.chatapp.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// 1. Vi tar bort @SpringBootTest. Vi behöver inte starta hela servern för
        // 2. Vi använder bara MockitoExtension.
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    // @Mock skapar ett "falskt" repository. Ingen databas är inblandad.
    @Mock
    private MessageRepository messageRepository;

    // @InjectMocks skapar instansen av Service och stoppar in vår mockade
    @InjectMocks
    private MessageService messageService;

    @Test
    void shouldSaveMessage() {
        // GIVEN
        Message message = new Message();
        message.setText("Hello World");

        // Här säger vi: "Om någon anropar save, returnera bara objektet".
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        // WHEN
        messageService.save(message);

        // THEN
        // Verifiera att repository.save() faktiskt anropades 1 gång.
        assertEquals("Hello World", message.getText());
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void shouldGetMessagesForUser() {
        // GIVEN
        Long userId = 1L;
        Message m1 = new Message();
        m1.setText("Hej");

        // Säg åt mocken: "Om någon ber om meddelanden för användare 1, ge dem
        when(messageRepository.findByUserId(userId)).thenReturn(List.of(m1));

        // WHEN
        List<Message> result = messageService.getMessages(userId);

        // THEN
        assertEquals(1, result.size());
        assertEquals("Hej", result.get(0).getText());
    }
}