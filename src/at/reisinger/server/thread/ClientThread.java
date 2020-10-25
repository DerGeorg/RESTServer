package at.reisinger.server.thread;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread{
    private final Socket       socket;
    private final int          id;
    private PrintStream out;
    private String       cmd;
    private String       url;
    private final StringBuilder rqBuilder;

    /**
     * Neuer Client wird erstellt
     * @param id Id des Clients
     * @param socket Socket des Clients
     */
    public ClientThread(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.rqBuilder = new StringBuilder();
    }

    /**
     * Hauptmethode des ClientThreads
     */
    @Override
    public void run() {
        try{
            System.out.println("Socket von Client #" + this.id + " wurde gestartet!");
            out = new PrintStream(socket.getOutputStream());
            InputStream in = socket.getInputStream();
            getRequest();
            createResponse();
            socket.close();
            System.out.println("Socket von Client #" + this.id + " wurde geschlossen!");
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Gibt alle Nachrichten aus
     */
    private void listAllMsg(){
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>GET /messages</TITLE>");
        out.print("<P>Listet alle Nachrichten</P>");
    }

    /**
     * gibt die startseite aus
     */
    private void startseite(){
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>Startseite</TITLE>");
        out.print("<p>lists all messages:   GET    /messages</p>" +
                "<p>add message:          POST   /messages     (Payload: the message; Response an id like 1)</p>" +
                "<p>show first message:   GET    /messages/1</p> " +
                "<p>show third message:   GET    /messages/3</p>" +
                "<p>update first message: PUT    /messages/1   (Payload: the message)</p>" +
                "<p>remove first message: DELETE /messages/1</p>");
    }

    /**
     * Erstellt eine Respone Message anhand des gegebenen Requests
     */
    private void createResponse() {
        if (this.url != null) {
            if (this.cmd.equals("GET")) {
                if (this.url.startsWith("/messages")) {
                    String lastBit = this.url.substring(this.url.lastIndexOf('/') + 1);
                    System.out.println("Last Bit: " + lastBit);
                    if(lastBit.equals("messages")){
                        listAllMsg();
                    }else{
                        getMsg(Integer.parseInt(lastBit));
                    }
                } else if (this.url.startsWith("/")) {
                    startseite();
                }
            }else if (this.cmd.equals("POST")){
                if (this.url.startsWith("/messages")) {
                    addMsg(id);
                }
            }else if (this.cmd.equals("PUT")){
                if (this.url.startsWith("/messages")) {
                    editMsg(id);
                }
            }else if (this.cmd.equals("DELETE")){
                if (this.url.startsWith("/messages")) {
                    delMsg(id);
                }
            }else{
                sendError();
            }
        }
    }

    /**
     * Holt eine Nachricht
     * @param id ID der nachricht die geholt werden soll
     */
    private void getMsg(int id) {
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>Get MSG</TITLE>");
        out.print("<p> Aktuelle MSG Number: " + id + "</p>");
    }

    /**
     * sendet eine Fehlermeldung zurück
     */
    private void sendError() {
        out.print("HTTP/1.0 500 ERR\r\n");
    }

    /**
     * Löscht eine Nachricht
     * @param id Nachricht die zu löschen ist
     */
    private void delMsg(int id) {
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>Del MSG</TITLE>");
        out.print("<p> Aktuelle MSG Number: " + id + "</p>");
    }

    /**
     * Bearbeitet eine Nachricht
     * @param id Nachricht die zu bearbeiten ist
     */
    private void editMsg(int id) {
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>Edit MSG</TITLE>");
        out.print("<p> Aktuelle MSG Number: " + id + "</p>");
    }

    /**
     * Fügt eine Nachricht hinzu
     * @param id Id der neuen Nachricht
     */
    private void addMsg(int id) {
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print("<TITLE>Add MSG</TITLE>");
        out.print("<p> Aktuelle MSG Number: " + id + "</p>");
    }

    /**
     * Holt den HTTP Request
     */
    private void getRequest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while (!(line = bufferedReader.readLine()).isBlank()) {
            rqBuilder.append(line + "\r\n");
        }
        String request = rqBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        this.url = path;
        this.cmd = method;

        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestsLines.length; h++) {
            String header = requestsLines[h];
            headers.add(header);
        }

        String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                socket.toString(), method, path, version, host, headers.toString());
        System.out.println(accessLog);

    }
}
