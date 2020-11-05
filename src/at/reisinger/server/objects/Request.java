package at.reisinger.server.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Verarbeitet einen Request
 * @author Georg Reisinger
 */
public class Request {

    private final Socket socket;
    private final int          id;
    private PrintStream out;
    private String       cmd;
    private String       url;
    private final StringBuilder rqBuilder;
    private String payload;

    /**
     * Get Request
     * @param socket Socket von dem der Request kommt
     * @param id Thread ID
     */
    public Request(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.rqBuilder = new StringBuilder();
        this.id = id;
        this.out = new PrintStream(this.socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = bufferedReader.readLine();
        while (!line.isBlank()) {
            rqBuilder.append(line + "\r\n");
            line = bufferedReader.readLine();
            System.out.println(line);
        }
        String request = rqBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        //code to read the post payload data
        StringBuilder payload = new StringBuilder();
        while(bufferedReader.ready()){
            payload.append((char) bufferedReader.read());
        }
        System.out.println("Payload: " + payload.toString());
        this.payload = payload.toString();

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



    /**
     * Get PrintStream --> Output
     *
     * @return out PrintStream --> Output
     */
    public PrintStream getOut() {
        return this.out;
    }

    /**
     * Command wie GET, PUT, POST, DEL
     *
     * @return cmd als String
     */
    public String getCmd() {
        return this.cmd;
    }

    /**
     * Request url
     *
     * @return url als String
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Payload des Request
     *
     * @return payload als String
     */
    public String getPayload() {
        return this.payload;
    }

}
