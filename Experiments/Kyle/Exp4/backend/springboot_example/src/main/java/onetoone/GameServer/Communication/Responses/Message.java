package onetoone.GameServer.Communication.Responses;

import onetoone.GameServer.PlayerClasses.Player;

import java.util.*;

public class Message {

    List<Player> players;

    String message;

    public Message(List<Player> players, String message){
        this.players = players;
        this.message = message;
    }

    public Message(Player player, String message){
        List<Player> plyrs = new ArrayList<>();
        plyrs.add(player);
        players = plyrs;
        this.message = message;
    }

    public Message(){
        this.players = new ArrayList<>();
        this.message = "default";
    }

    public void addPlayer(Player player){
        players.add(player);
    }

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
