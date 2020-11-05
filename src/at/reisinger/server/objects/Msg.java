package at.reisinger.server.objects;

/**
 * Message Objekt
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
     * set field
     *
     * @param id Message Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get field
     *
     * @return msg Message String
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * set field
     *
     * @param msg Message String
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
