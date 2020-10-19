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
     * Erstellt eine Respone Message anhand des gegebenen Requests
     */
    private void createResponse() {
        if (this.url != null) {
            if (this.cmd.equals("GET")) {
                if (this.url.startsWith("/test")) {
                    out.print("HTTP/1.0 200 OK\r\n");
                    out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    out.print("Server: Apache/0.8.4\r\n");
                    out.print("Content-Type: text/html\r\n");
                    out.print("Content-Length: 59\r\n");
                    out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    out.print("\r\n");
                    out.print("<TITLE>Test</TITLE>");
                    out.print("<P>Das ist ein Test</P>");
                } else if (this.url.startsWith("/")) {
                    out.print("HTTP/1.0 200 OK\r\n");
                    out.print("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    out.print("Server: Apache/0.8.4\r\n");
                    out.print("Content-Type: text/html\r\n");
                    out.print("Content-Length: 59\r\n");
                    out.print("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    out.print("\r\n");
                    out.print("<TITLE>Hello World!</TITLE>");
                    out.print("<P>Hallo Welt!</P>");
                }
            }else if (this.cmd.equals("POST")){

            }else if (this.cmd.equals("PUT")){

            }else if (this.cmd.equals("DELETE")){

            }else{
                /*

                ERROR senden


                 */
            }
        }
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
