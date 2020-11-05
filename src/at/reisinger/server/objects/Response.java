package at.reisinger.server.objects;

import at.reisinger.server.msg.MsgHandler;

import java.io.PrintStream;
import java.net.Socket;

public class Response {

    //private final Socket socket;
    private final int          id;
    private PrintStream out;
    private String       cmd;
    private String       url;
    private final StringBuilder rqBuilder;
    private MsgHandler msgHandler;

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
                        sendResponse(msgHandler.getMsg(Integer.parseInt(lastBit)).getMsg());
                    }
                } else if (this.url.startsWith("/")) {
                    startseite();
                }
            }else if (this.cmd.equals("POST")){
                if (this.url.startsWith("/messages")) {
                    sendResponse(msgHandler.addMsg(payload) + "");
                }
            }else if (this.cmd.equals("PUT")){
                if (this.url.startsWith("/messages")) {
                    msgHandler.editMsg(id, "Edit");
                }
            }else if (this.cmd.equals("DELETE")){
                if (this.url.startsWith("/messages")) {
                    msgHandler.delMsg(id);
                }
            }else{
                sendResponse(sendError());
            }
        }
    }



    private String sendError() {
        return "";
    }

    private void startseite() {
        sendResponse("lists all messages:   GET    /messages<br>" +
                "add message:          POST   /messages     (Payload: the message; Response an id like1)<br>" +
                "show first message:   GET    /messages/1<br>" +
                "show third message:   GET    /messages/3<br>" +
                "update first message: PUT    /messages/1   (Payload: the message)<br>" +
                "remove first message: DELETE /messages/1<br>");
    }

    private void listAllMsg() {
        sendResponse(msgHandler.getAllMsg());
        //sendResponse("Test");
    }

    private void sendResponse(String responseText){
        out.print("HTTP/1.0 200 OK\r\n");
        out.print("Date: Fri, 31 Dec 2020 23:59:59 GMT\r\n");
        out.print("Server: Apache/0.8.4\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("Content-Length: 59\r\n");
        out.print("Expires: Sat, 01 Jan 2025 00:59:59 GMT\r\n");
        out.print("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.print("\r\n");
        out.print(responseText);
    }

    /**
     * get field
     *
     * @return out
     */
    public PrintStream getOut() {
        return this.out;
    }

    /**
     * set field
     *
     * @param out
     */
    public void setOut(PrintStream out) {
        this.out = out;
    }

    /**
     * get field
     *
     * @return cmd
     */
    public String getCmd() {
        return this.cmd;
    }

    /**
     * set field
     *
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * get field
     *
     * @return url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * set field
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * get field
     *
     * @return msgHandler
     */
    public MsgHandler getMsghandler() {
        return this.msgHandler;
    }

    /**
     * set field
     *
     * @param msgHandler
     */
    public void setMsghandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }
}
