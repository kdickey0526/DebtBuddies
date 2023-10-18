package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.Player;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class GoFish {

    private List<GoFishPlayer> players;

    private int gameId;

    public GoFish(List<GoFishPlayer> players, int gameId){
        this.players = players;
        this.gameId = gameId;
    }

    public Response getResponse(GoFishPlayer player, ServerEvent serverEvent){
        return new Response(new Message(player, "hello there"));
    }

    public List<Player> castToPlayers(){
        List<Player> temp = new ArrayList<>();
        for (GoFishPlayer player : players) {
            temp.add((Player) player);
        }
        return temp;
    }

    public int getGameId(){
        return gameId;
    }

}
