package at.reisinger.server.thread;

import at.reisinger.server.msg.MsgHandler;
import at.reisinger.server.objects.Request;
import at.reisinger.server.objects.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread{
    private final Socket socket;
    private final int          id;
    private MsgHandler msgHandler;

    /**
     * Neuer Client wird erstellt
     * @param id Id des Clients
     * @param socket Socket des Clients
     */
    public ClientThread(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    /**
     * Hauptmethode des ClientThreads
     */
    @Override
    public void run() {
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
