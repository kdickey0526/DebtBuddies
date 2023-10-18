package onetoone.Events;

public class GameEvent {

    private String event;

    private String data;

    public GameEvent(String event, String data){
        this.event = event;
        this.data = data;
    }

    public String getEvent(){
        return event;
    }

    public String getData(){
        return data;
    }

}
