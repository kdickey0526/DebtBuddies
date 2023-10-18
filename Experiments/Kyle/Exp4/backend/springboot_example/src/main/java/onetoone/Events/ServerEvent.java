package onetoone.Events;

public class ServerEvent {

    private String game;

    private int gameId;

    private Action action;
    public ServerEvent(String game, int gameId, Action action){
        this.game = game;
        this.gameId = gameId;
        this.action = action;
    }

    public String getGame(){
        return game;
    }

    public int getGameId(){
        return gameId;
    }

    public Action getAction(){
        return action;
    }

}
