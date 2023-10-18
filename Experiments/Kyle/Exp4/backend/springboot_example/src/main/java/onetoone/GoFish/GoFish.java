package onetoone.GoFish;

import onetoone.Events.Action;
import onetoone.PlayerClasses.GoFishPlayer;
import onetoone.PlayerClasses.Player;
import onetoone.PlayerClasses.TexasHoldEmPlayer;
import onetoone.Responses.Message;
import onetoone.Responses.Response;

import java.util.*;

public class GoFish {

    private List<GoFishPlayer> players;

    private int gameId;

    public GoFish(List<GoFishPlayer> players, int gameId){
        this.players = players;
        this.gameId = gameId;
    }

    public Response getResponse(GoFishPlayer player, Action action){
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
