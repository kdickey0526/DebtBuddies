package onetoone.GoFish;

import onetoone.Events.Action;
import onetoone.PlayerClasses.GoFishPlayer;
import onetoone.PlayerClasses.Player;
import onetoone.PlayerClasses.TexasHoldEmPlayer;
import onetoone.Responses.Message;
import onetoone.Responses.Response;

import java.util.*;

public class GoFishManager {

    private static int GAMEID = 0;

    private static List<GoFishPlayer> Queue = new ArrayList<>();

    private static List<GoFish> Servers = new ArrayList<>();

    private static Map < Player, GoFishPlayer > playerFishMap = new Hashtable<>();

    private static Map < String, Integer > usernameGameIdMap = new Hashtable<>();

    public static Response getResponse(Player player, Action action){
        Response response = new Response();

        if(!usernameGameIdMap.containsKey(player.toString())){
            GoFishPlayer new_player = new GoFishPlayer(player);
            playerFishMap.put(player, new_player);
            Queue.add(new_player);
            if(Queue.size() == 2){
                GoFish fish_game = new GoFish(Queue, ++GAMEID);
                for(Player temp_player : Queue) {
                    usernameGameIdMap.put(temp_player.toString(), GAMEID);
                }
                Servers.add(fish_game);
                response.addMessage(new Message(fish_game.castToPlayers(), "{\"action\":\"join\",\"value\":{\"gameid\":"+GAMEID+"}"));
                Queue.clear();
            }else{
                response.addMessage(new Message(player, "{\"action\":\"Q\"}"));
            }
        }else{
            for(GoFish server : Servers){
                if(usernameGameIdMap.get(player.toString()) == server.getGameId()){
                    GoFishPlayer t_player = getFishPlayer(player);
                    response.addResponse(server.getResponse(t_player, action));
                }
            }
        }
        return response;
    }

    private static GoFishPlayer getFishPlayer(Player player){
        return playerFishMap.get(player);
    }

}
