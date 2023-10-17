package onetoone.GameServer;

import java.util.*;

public class Response {

    private List<Player> players;

    String message;

    public Response(List<Player> players, String message){
        this.players = players;
        this.message = message;
    }

    public void setPlayers(List<Player> players){ this.players = players; }

    public void setMessage(String message){
        this.message = message;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public String getMessage(){
        return message;
    }

}
