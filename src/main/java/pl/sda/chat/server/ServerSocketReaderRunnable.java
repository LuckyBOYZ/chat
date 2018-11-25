package pl.sda.chat.server;

import pl.sda.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        boolean register = chatLog.register(client);
        if (register) {
            try (ObjectInputStream feedbackMessage = new ObjectInputStream(client.getInputStream())) {
                ChatMessage message;
                while (true) {
                    message = (ChatMessage) feedbackMessage.readObject();

                    if (message.getMessage().equalsIgnoreCase("exit")) {
                        break;
                    }
                    chatLog.acceptMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            chatLog.unregister(client);
        }
    }
}
