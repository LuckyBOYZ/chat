package pl.sda.chat.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatedChatMessageTest {

    @Test
    public void shouldReturnCorrectValuesInObjectChatMessage(){
        //given
        String author = "Łukasz";
        String message = "Hello";
        ChatMessage chatMessage = new ChatMessage(author, message);
        //when
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);
        //then
        assertEquals(author , datedChatMessage.getAuthor());
        assertEquals(message , datedChatMessage.getMessage());
        assertNotNull(datedChatMessage.getReceiveDate());
    }

}