package onetoone.GameServer.Communication.Events;
import com.google.gson.Gson;
public class GameEvent {

    private static Gson gson = new Gson();

    private String event;

    private String data;

    public GameEvent(String event, String data){
        this.event = event;
        this.data = data;
    }

    public GameEvent(String event){
        this.event = event;
        this.data = "{}";
    }

    public String getEvent(){
        return event;
    }

    public String getData(){
        return data;
    }

    @Override
    public String toString(){
        return gson.toJson(this);
    }

}
