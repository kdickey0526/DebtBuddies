package onetoone.GameServer.Communication.Responses;

import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.Communication.MessageBearer;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class Response {

   // private static List<Message> messages = new ArrayList<>();

    private static List<MessageBearer> messages = new ArrayList<>();

    public static void addMessage(List<User> users, String type, String data){
        messages.add(new MessageBearer(users, type, data));
    }

    public static List<MessageBearer> getMessages(){
        return messages;
    }

    public static void clearMessages(){
        messages.clear();
    }

    /*

    public static void addMessage(List<User> users, String event, String data){
        GameEvent gameEvent = new GameEvent(event, data);
        addMessage(new Message(users, gameEvent.toString()));
    }

    public static void addMessage(User user, String event, String data){
        GameEvent gameEvent = new GameEvent(event, data);
        List<User> users = new ArrayList<>();
        users.add(user);
        addMessage(new Message(users, gameEvent.toString()));
    }

    public static void addMessage(Message message){
        messages.add(message);
    }

    public static List<Message> getMessages(){
        return messages;
    }

    public static void clearMessages(){
        messages.clear();
    }

     */

}
