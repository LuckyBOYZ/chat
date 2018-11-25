package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerSocketReaderRunnable implements Runnable {
    private final Socket client;
    private final ChatLog chatLog;

    public ServerSocketReaderRunnable(Socket client, ChatLog chatLog) {
        this.client = client;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        if (chatLog.register(client)) {
            try (ObjectInputStream feedbackMessage = new ObjectInputStream(client.getInputStream())) {
                while (true) {
                    ChatMessage message = (ChatMessage) feedbackMessage.readObject();

                    if (message.getMessage().equalsIgnoreCase("exit") || message.getMessage() == null) {
                        break;
                    }
                    chatLog.acceptMessage(message);
                }
            } catch (IOException e) {
                System.out.println("### Client disconnected due to network problem ###");
            } catch (ClassNotFoundException e) {
                System.out.println("### Client disconnected due to invalid format ###");
            }
            chatLog.unregister(client);
        }
    }
}
