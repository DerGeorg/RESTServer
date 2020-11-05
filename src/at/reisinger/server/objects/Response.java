package at.reisinger.server.objects;

import at.reisinger.server.msg.MsgHandler;

import java.io.PrintStream;
import java.net.Socket;

/**
 * Erstellt und sendet eine Response anhand des Requests
 * @author Georg Reisinger
 */
public class Response {

    //private final Socket socket;
    private final int          id;
    private PrintStream out;
    private String       cmd;
    private String       url;
    private MsgHandler msgHandler;
    private StringBuilder rqBuilder;

    /**
     * Nimmt die Daten des requests und generiert eine Response
     * @param id Thread Id
     * @param url Request Url
     * @param cmd Request CMD
     * @param out out Print Stream
     * @param msgHandler Der MsgHandler
     * @param payload Payload des Requests
     */
    public Response(int id, String url, String cmd, PrintStream out, MsgHandler msgHandler, String payload){
        this.id = id;
        this.msgHandler = msgHandler;
        this.url = url;
        this.cmd = cmd;
        this.out = out;
        this.rqBuilder = new StringBuilder();
        System.out.println(cmd);
        if (this.url != null) {
            if (this.cmd.equals("GET")) {
                if (this.url.startsWith("/messages")) {
                    String lastBit = this.url.substring(this.url.lastIndexOf('/') + 1);
                    System.out.println("Last Bit: " + lastBit);
                    if(lastBit.equals("messages")){
                        listAllMsg();
                    }else{
                        String message = msgHandler.getMsg(Integer.parseInt(lastBit)).getMsg();
                        if(message == null){
                            sendError("404");
                        }else {
                            sendResponse(message, "200");
                        }
                    }
                } else if (this.url.startsWith("/")) {
                    startseite();
                }
            }else if (this.cmd.equals("POST")){
                if (this.url.startsWith("/messages")) {
                    sendResponse(msgHandler.addMsg(payload) + "", "201");
                }
            }else if (this.cmd.equals("PUT")){
                if (this.url.startsWith("/messages")) {
                    String lastBit = this.url.substring(this.url.lastIndexOf('/') + 1);
                    System.out.println("Last Bit: " + lastBit);
                    System.out.println("Payload" + payload);
                    String message = msgHandler.editMsg(Integer.parseInt(lastBit), payload);
                    if(message == null){
                        sendError("404");
                    }else {
                        sendResponse("","200");
                    }
                }
            }else if (this.cmd.equals("DELETE")){
                if (this.url.startsWith("/messages")) {
                    String lastBit = this.url.substring(this.url.lastIndexOf('/') + 1);
                    String message = msgHandler.delMsg(Integer.parseInt(lastBit));
                    if(message == null){
                        sendError("404");
                    }else {
                        sendResponse("", "200");
                    }
                }
            }else{
                sendError("405");
            }
        }
    }


    /**
     * Sendet einen Error Response
     * @param errorCode Der Error Code
     */
    private void sendError(String errorCode) {
        out.print("HTTP/1.0 "+errorCode+"\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("Content-Length: 1\r\n");
        out.print("\r\n");
        //out.print(responseText);
    }

    private void startseite() {
        sendResponse("lists all messages:   GET    /messages<br>" +
                "add message:          POST   /messages     (Payload: the message; Response an id like1)<br>" +
                "show first message:   GET    /messages/1<br>" +
                "show third message:   GET    /messages/3<br>" +
                "update first message: PUT    /messages/1   (Payload: the message)<br>" +
                "remove first message: DELETE /messages/1<br>", "200");
    }

    private void listAllMsg() {
        sendResponse(msgHandler.getAllMsg(), "200");
        //sendResponse("Test");
    }

    /**
     * Sendet eine Response
     * @param responseText Text der zu senden ist
     * @param code Http code
     */
    private void sendResponse(String responseText, String code){
        out.print("HTTP/1.0 "+code+"\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("Content-Length: "+responseText.length()+"\r\n");
        out.print("\r\n");
        out.print(responseText);
    }

    /**
     * Get Msg Handler
     *
     * @return msgHandler Handler der Nachrichten
     */
    public MsgHandler getMsghandler() {
        return this.msgHandler;
    }

}
