package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Games.Game;
import onetoone.GameServer.Games.GameInterface;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmPlayer;
import onetoone.GameServer.PlayerClasses.Player;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class GoFish extends Game<GoFishPlayer> implements GameInterface<GoFishPlayer> {

    public GoFish(List<GoFishPlayer> players, int gameId){
        super(players, gameId);
    }

    @Override
    protected void initializeGame() {

    }

    public Response getResponse(GoFishPlayer player, ServerEvent serverEvent){
        return new Response(new Message(player, "hello there"));
    }

}
