package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> registeredClient;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChatLog() {
        registeredClient = new ConcurrentHashMap<>();
    }

    public boolean register(Socket client) {
        try {
            ObjectOutputStream streamToClient = new ObjectOutputStream(client.getOutputStream());
            registeredClient.put(client, streamToClient);
            return true;
        } catch (IOException e) {
            System.out.println("### Someone tried to connect but not rejected ###");
            return false;
        }

    }

    public boolean unregister(Socket client) {
        try {
            ObjectOutputStream streamToClient = registeredClient.get(client);
            registeredClient.remove(client);
            streamToClient.close();
            return true;
        } catch (IOException e) {
            System.out.println("### Can not remove client, please check connecting to server ###");
            return false;
        }
    }

    public void acceptMessage(ChatMessage chatMessage) {
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);
        printMessage(datedChatMessage);
        updatedClients(datedChatMessage);
    }

    private void printMessage(DatedChatMessage datedChatMessage){
        System.out.println("<" + dateFormatter.format(datedChatMessage.getReceiveDate()) + ">" + "<" + datedChatMessage.getAuthor() + ">" + "<" + datedChatMessage.getMessage() + ">");
    }

    private void updatedClients (DatedChatMessage datedChatMessage){
        Set<Map.Entry<Socket, ObjectOutputStream>> entries = registeredClient.entrySet();
        for (Map.Entry<Socket, ObjectOutputStream> entry : entries){
            ObjectOutputStream connectionToClient = entry.getValue();
            try {
                connectionToClient.writeObject(datedChatMessage);
            } catch (IOException e) {
                unregister(entry.getKey());
            }
        }
    }
}
