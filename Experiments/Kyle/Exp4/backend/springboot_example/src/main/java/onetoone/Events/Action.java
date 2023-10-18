package onetoone.Events;

public class Action {
    private String action;

    private int value;

    public Action(String action, int value){
        this.action = action;
        this.value = value;
    }

    public Action(String action){
        this.action = action;
    }

    public String getAction(){
        return action;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return "\nAction: " + action + "\nValue: " + Integer.toString(value);
    }

}
