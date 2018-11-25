package pl.sda.chat.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DatedChatMessageTest {

    @Test
    public void shouldReturnCorrectValuesInObjectChatMessage(){
        //given
        DatedChatMessage datedChatMessage = new DatedChatMessage(new ChatMessage("xxx" , "yyy"));
        //when
        String author = datedChatMessage.getAuthor();
        String message = datedChatMessage.getMessage();
        LocalDate receiveDate = datedChatMessage.getReceiveDate();
        //then
        assertEquals("xxx" , author);
        assertEquals("yyy" , message);
        assertNotNull(receiveDate);
    }

}