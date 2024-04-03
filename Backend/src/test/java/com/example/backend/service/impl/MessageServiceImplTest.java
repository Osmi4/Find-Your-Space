import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.service.MessageService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {

    @Test
    void testAddMessage() {
        // Mock dependencies
//        MessageService messageService = mock(MessageService.class);
//        AddMessage addMessage = new AddMessage();
//        addMessage.setMessageContent("Hello, world!");
//        addMessage.setMessageDestinationEmail("receiver@example.com");
//        addMessage.setSender("sender@example.com");
//        addMessage.setReceiver("receiver@example.com");
//
//        MessageResponse expectedResponse = new MessageResponse();
//        expectedResponse.setMessageId("123");
//        expectedResponse.setMessageContent(addMessage.getMessageContent());
//        expectedResponse.setMessageDestinationEmail(addMessage.getMessageDestinationEmail());
//        expectedResponse.setSender(addMessage.getSender());
//        expectedResponse.setReceiver(addMessage.getReceiver());
//
//        // Test
//        MessageResponse actualResponse = messageService.addMessage(addMessage);
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getMessageId(), actualResponse.getMessageId());
//        assertEquals(expectedResponse.getMessageContent(), actualResponse.getMessageContent());
//        assertEquals(expectedResponse.getMessageDestinationEmail(), actualResponse.getMessageDestinationEmail());
//        assertEquals(expectedResponse.getSender(), actualResponse.getSender());
//        assertEquals(expectedResponse.getReceiver(), actualResponse.getReceiver());
    }

    @Test
    void testGetMessage() {
        // Mock dependencies
        MessageService messageService = mock(MessageService.class);
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessageId("123");

        // Test
        MessageResponse actualResponse = messageService.getMessage("123");

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getMessageId(), actualResponse.getMessageId());
    }

    @Test
    void testDeleteMessage() {
        // Mock dependencies
        MessageService messageService = mock(MessageService.class);
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessageId("123");

        // Test
        MessageResponse actualResponse = messageService.deleteMessage("123");

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getMessageId(), actualResponse.getMessageId());
    }
}
