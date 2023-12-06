package debtbuddies.GameServer.Communication;

import debtbuddies.GameServer.PlayerClasses.User;

import java.util.*;
import com.google.gson.Gson;

public class MessageBearer {

    private static Gson gson = new Gson();

    private List<User> recipients;

    private MessageWrapper message;

    public MessageBearer(List<User> recipients, String type, Object info){
        this.recipients = recipients;
        this.message = new MessageWrapper(type, gson.toJson(info));
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public String getMessageString(){
        if(Objects.equals(message.getType(), "playInfo")){
            return message.getData();
        }
        return gson.toJson(message);
    }

}
