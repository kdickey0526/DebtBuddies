package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Games.Game;
import onetoone.GameServer.Games.GameInterface;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class GoFish extends Game<GoFishUser> implements GameInterface<GoFishUser, GoFish> {

    public GoFish(List<GoFishUser> users, int gameId){
        super(users, gameId, 2);
    }

    @Override
    protected void initializeGame() {

    }

    public void getResponse(GoFishUser user, ServerEvent serverEvent){
        Response.addMessage(user, "message", "hello there");
    }

    @Override
    public GoFish getNewGame(List<GoFishUser> queue, int gameId) {
        return null;
    }

    @Override
    public GoFishUser getNewUser(User user) {
        return null;
    }

    @Override
    public int getQueueSize() {
        return 0;
    }

}
