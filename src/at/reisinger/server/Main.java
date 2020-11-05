package at.reisinger.server;

import at.reisinger.server.msg.MsgHandler;
import at.reisinger.server.objects.Request;
import at.reisinger.server.objects.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Grundlegende Server logic
 * Vereint alle anderen Klassen
 */
public class Main {
    static final int port = 80;
    private Socket socket;
    private int id;
    private MsgHandler msgHandler;

    /**
     * Initial Start
     * @param args Nicht Verwendet
     */
    public static void main(String[] args) {
        System.out.println("Starte Server auf Port 80");
        new Main(port);
    }

    /**
     * Öffnet den Server Socket und akzepiert diesen
     * @param port Port auf dem der Server läuft
     */
    public Main(int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            this.msgHandler = new MsgHandler();
            this.id = 0;
            while (true){
                this.socket = serverSocket.accept();
                requestResponding();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Jeder Request durchläuft diese Funktion, reagiert auf requests
     */
    public void requestResponding(){
        try{
            System.out.println("Socket von Client #" + this.id + " wurde gestartet!");
            Request rq = new Request(this.socket, this.id);
            Response rp = new Response(this.id, rq.getUrl(), rq.getCmd(), rq.getOut(), this.msgHandler, rq.getPayload());
            this.msgHandler = rp.getMsghandler();
            this.socket.close();
            System.out.println("Socket von Client #" + this.id + " wurde geschlossen!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
