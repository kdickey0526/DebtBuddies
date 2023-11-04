package onetoone.GameServer.Communication.Events;

public class ServerEvent {

    private String action;

    private int value;

    public ServerEvent(String action, int value){
        this.action = action;
        this.value = value;
    }

    public ServerEvent(String action){
        this.action = action;
    }

    public String getAction(){ return action; }

    public int getValue(){ return value; }

}
