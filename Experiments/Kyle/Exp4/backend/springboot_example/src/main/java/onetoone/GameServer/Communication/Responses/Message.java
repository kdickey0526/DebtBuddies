package onetoone.GameServer.Communication.Responses;

import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class Message {

    List<User> users;

    String message;

    public Message(List<User> users, String message){
        this.users = users;
        this.message = message;
    }

    public Message(User user, String message){
        List<User> plyrs = new ArrayList<>();
        plyrs.add(user);
        users = plyrs;
        this.message = message;
    }

    public Message(){
        this.users = new ArrayList<>();
        this.message = "default";
    }

    public void addPlayer(User user){
        users.add(user);
    }

    public void setMessage(String message){
        this.message = message;
    }
    public List<User> getPlayers(){
        return users;
    }

    public String getMessage(){
        return message;
    }

}
