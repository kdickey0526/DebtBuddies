package onetoone.GameServer.Communication;

import onetoone.GameServer.Communication.MessageBearer;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class Response {

    private static List<MessageBearer> messages = new ArrayList<>();

    public static void addMessage(User user, String type, Object obj){
        List<User> users = new ArrayList<>();
        users.add(user);
        messages.add(new MessageBearer(users, type, obj));
    }

    public static void addMessage(List<User> users, String type, Object obj){
        messages.add(new MessageBearer(users, type, obj));
    }

    public static List<MessageBearer> getMessages(){
        return messages;
    }

    public static void clearMessages(){
        messages.clear();
    }

}
