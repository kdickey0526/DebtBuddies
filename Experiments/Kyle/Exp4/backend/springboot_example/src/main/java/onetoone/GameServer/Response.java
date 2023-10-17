package onetoone.GameServer;

import java.util.*;

public class Response {

    private List<Player> players;

    private List<String> messages;

    public Response(List<Player> players, List<String> messages){
        this.players = players;
        this.messages = messages;
    }

    public Response(){
        players = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void setPlayers(List<Player> players){ this.players = players; }

    public void setMessages(List<String> messages){
        this.messages = messages;
    }

    public void setMessage(String message){
        List<String> msgs = new ArrayList<>();
        msgs.add(message);
        messages = msgs;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void addMessage(String message){ messages.add(message); }

    public List<Player> getPlayers(){
        return players;
    }

    public List<String> getMessages(){
        return messages;
    }

    public String getMessage(int index){ return messages.get(index); }

}
