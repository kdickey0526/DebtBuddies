package onetoone.GameServer.Communication.Responses;

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
