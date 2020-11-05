package test;

import at.reisinger.server.msg.MsgHandler;
import at.reisinger.server.objects.Msg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class MyTests{
    @Test
    @DisplayName("Msg - GetMsg")
    public void msgTest(){
        Msg msg = new Msg(1, "Nachricht");
        assertTrue(msg.getMsg().equals("Nachricht"));
    }

    @Test
    @DisplayName("Msg - getId")
    public void msgIdTest(){
        Msg msg = new Msg(1, "Nachricht");
        assertTrue(msg.getId() == 1);
    }

    @Test
    @DisplayName("MsgHandler - GetMsg")
    public void msgHandlerGetTest(){
        MsgHandler msgHandler = new MsgHandler();
        assertTrue(msgHandler.getMsg(5).getMsg().equals("Dir?"));
    }

    @Test
    @DisplayName("MsgHandler - AddMsg")
    public void msgHandlerAddTest(){
        MsgHandler msgHandler = new MsgHandler();
        msgHandler.addMsg("Nachricht"); //id = 6
        assertTrue(msgHandler.getMsg(6).getMsg().equals("Nachricht"));
    }

    @Test
    @DisplayName("MsgHandler - editMsg")
    public void msgHandlerEditTest(){
        MsgHandler msgHandler = new MsgHandler();
        msgHandler.addMsg("Nachricht"); //id = 6
        msgHandler.editMsg(6, "Neu");
        assertTrue(msgHandler.getMsg(6).getMsg().equals("Neu"));
    }

    @Test
    @DisplayName("MsgHandler - DelMsg")
    public void msgHandlerDelTest(){
        MsgHandler msgHandler = new MsgHandler();
        msgHandler.addMsg("Nachricht"); //id = 6
        msgHandler.delMsg(6);
        assertTrue(msgHandler.getMsg(6).getMsg() == null);
    }
}