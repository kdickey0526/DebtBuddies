package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Games.Game;
import onetoone.GameServer.Games.GameInterface;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class GoFish extends Game<GoFishUser> implements GameInterface<GoFishUser> {

    public GoFish(List<GoFishUser> users, int gameId){
        super(users, gameId);
    }

    @Override
    protected void initializeGame() {

    }

    public Response getResponse(GoFishUser user, ServerEvent serverEvent){
        return new Response(new Message(user, "hello there"));
    }

}
