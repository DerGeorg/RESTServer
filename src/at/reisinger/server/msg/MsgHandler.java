package at.reisinger.server.msg;

import at.reisinger.server.objects.Msg;

import java.util.HashMap;

public class MsgHandler {
    private HashMap<Integer , String> msgHashMap;
    private int lastID;

    /**
     * Erstellt den MsgHandler mit standart Test Messages
     */
    public MsgHandler(){
        msgHashMap = new HashMap<Integer, String>();
        addMsg("Hallo");
        addMsg("Wie");
        addMsg("Geht");
        addMsg("Es");
        addMsg("Dir?");

    }

    /**
     * Ermitelt die nächste freie Id
     * @return Next ID
     */
    public int nextId(){
        return this.lastID + 1;
    }

    /**
     * Msg hinzufügen
     * @param msg Message Text
     */
    public int addMsg(String msg){
        int id = nextId();
        msgHashMap.put(id, msg);
        this.lastID = id;
        return id;
    }

    /**
     * Msg löschen
     * @param id Message Id
     */
    public void delMsg(int id){
        msgHashMap.remove(id);
    }

    /**
     * Msg bearbeiten
     * @param id Message Id
     * @param msg Message Text
     */
    public void editMsg(int id, String msg){
        msgHashMap.replace(id, msg);
    }

    /**
     * Msg als Objekt holen
     * @param id Message Id
     * @return Message als Msg Objekt
     */
    public Msg getMsg(int id){
        return new Msg(id, msgHashMap.get(id));
    }

    /**
     * Alle Nachrichten werden in den Format Key, Value besorgt
     * bsp: key: 1 value: Nachricht
     * @return Alle nachrichten in einem String
     */
    public String getAllMsg(){
        String returnStr = "";
        // Print keys and values
        for (Integer i : msgHashMap.keySet()) {
            String item = "key: " + i + " value: " + msgHashMap.get(i);
            returnStr += item;
        }
        System.out.println(returnStr);
        return returnStr;
    }
}
