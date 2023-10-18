package onetoone.GameServer;

public class Event {

    String event;

    Action action;

    public Event(String event, Action action){
        this.event = event;
        this.action = action;
    }

    public String getEvent(){
        return event;
    }

    public Action getAction(){
        return action;
    }

    @Override
    public String toString(){
        return "Event: " + event + "\nAction: " + action.toString();
    }

}
