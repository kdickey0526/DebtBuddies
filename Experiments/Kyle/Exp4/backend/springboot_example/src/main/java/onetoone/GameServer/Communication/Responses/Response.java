package onetoone.GameServer.Communication.Responses;

import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class Response {

    private List<Message> messages;

    public Response(){
        messages = new ArrayList<>();
    }

    public Response(Message message){
        List<Message> msgs = new ArrayList<>();
        msgs.add(message);
        messages = msgs;
    }

    public Response(List<Message> messages){
        this.messages = messages;
    }

    public void addMessage(List<User> users, String event, String data){
        GameEvent gameEvent = new GameEvent(event, data);
        messages.add(new Message(users, gameEvent.toString()));
    }

    public void addMessage(User user, String event, String data){
        GameEvent gameEvent = new GameEvent(event, data);
        List<User> users = new ArrayList<>();
        users.add(user);
        messages.add(new Message(users, gameEvent.toString()));
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public List<Message> getMessages(){
        return messages;
    }

    public void addResponse(Response response){
        messages.addAll(response.getMessages());
    }

}
