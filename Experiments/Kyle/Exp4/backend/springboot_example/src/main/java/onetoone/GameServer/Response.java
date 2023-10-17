package onetoone.GameServer;

public class Response {

    String username;

    String message;

    public Response(String username, String message){
        this.username = username;
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getUsername(){
        return username;
    }

    public String getMessage(){
        return message;
    }

}
