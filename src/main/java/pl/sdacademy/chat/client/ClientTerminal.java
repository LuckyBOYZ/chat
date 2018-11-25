package pl.sdacademy.chat.client;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {

    private final Socket connectionsToServer;

    public ClientTerminal() throws IOException {
        connectionsToServer = new Socket("192.168.8.4", 5567);
    }

    @Override
    public void run() {
        try (ObjectOutputStream streamToServer = new ObjectOutputStream(connectionsToServer.getOutputStream())) {
            System.out.print("Insert yout nick: ");
            String nick = new Scanner(System.in).nextLine();
            String clientMessage;
            while (true) {
                System.out.print("Insert your message: ");
                clientMessage = new Scanner(System.in).nextLine();
                ChatMessage chatMessage = new ChatMessage(nick, clientMessage);
                streamToServer.writeObject(chatMessage);
                streamToServer.flush();
                if (clientMessage.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            System.out.println("Disconnecting");

        } catch (IOException e) {
            System.out.println("Server closed connection.");
            e.printStackTrace();
        }
    }
}
