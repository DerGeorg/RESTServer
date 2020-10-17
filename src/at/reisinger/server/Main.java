package at.reisinger.server;

import at.reisinger.server.thread.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static final int port = 80;

    public static void main(String[] args) {
        System.out.println("Starte Server auf Port 80");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            int id = 0;
            while (true){
                Socket socket = serverSocket.accept();
                new ClientThread(id, socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
