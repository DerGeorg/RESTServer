package at.reisinger.server.objects;

/**
 * Message Objekt beinmhaltet die MsgId und die Msg selbst
 * @author Georg Reisinger
 */
public class Msg {
    private int id;
    private String msg;

    /**
     * Erstellt eine Message
     */
    public Msg(int id, String msg){
        this.id = id;
        this.msg = msg;
    }


    /**
     * get field
     *
     * @return id Message Id
     */
    public int getId() {
        return this.id;
    }

    /**
     * get field
     *
     * @return msg Message String
     */
    public String getMsg() {
        return this.msg;
    }
}
