package pl.sdacademy.chat.model;

import java.time.LocalDate;

public class DatedChatMessage extends ChatMessage {
    private final LocalDate receiveDate;

    public DatedChatMessage(ChatMessage chatMessage) {
        super(chatMessage.getAuthor(), chatMessage.getMessage());
        receiveDate = LocalDate.now();
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }
}
