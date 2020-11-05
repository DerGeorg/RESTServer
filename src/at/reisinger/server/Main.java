package at.reisinger.server;

import at.reisinger.server.msg.MsgHandler;
import at.reisinger.server.thread.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

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
                Thread client = new ClientThread(id, socket);
                client.start();
                try {
                    client.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
