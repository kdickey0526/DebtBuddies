package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.Player;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class TexasHoldEmManager {

    private static int GAMEID = 0;

    private static List<TexasHoldEmPlayer> Queue = new ArrayList<>();

    private static List<TexasHoldEm> Servers = new ArrayList<>();

    private static Map < Player, TexasHoldEmPlayer > playerTexasMap = new Hashtable<>();

    private static Map < String, Integer > usernameGameIdMap = new Hashtable<>();

    public static Response getResponse(Player player, ServerEvent serverEvent){
        Response response = new Response();

        if(!usernameGameIdMap.containsKey(player.toString()) && Objects.equals(serverEvent.getAction(), "joinQueue")){
            TexasHoldEmPlayer new_player = new TexasHoldEmPlayer(player);
            playerTexasMap.put(player, new_player);
            Queue.add(new_player);
            if(Queue.size() == 3){
                TexasHoldEm texas_game = new TexasHoldEm(Queue, ++GAMEID);
                for(Player temp_player : Queue) {
                    usernameGameIdMap.put(temp_player.toString(), GAMEID);
                }
                Servers.add(texas_game);
                response.addMessage(new Message(texas_game.getAllPlayers(), "{\"action\":\"join\",\"value\":{\"gameid\":"+GAMEID+"}}"));
                Queue.clear();
            }else{
                response.addMessage(new Message(player, "{\"action\":\"Q\"}"));
            }
        }else{
            for(TexasHoldEm server : Servers){
                if(usernameGameIdMap.get(player.toString()) == server.getGameId()){
                    TexasHoldEmPlayer t_player = getTexasPlayer(player);
                    response.addResponse(server.getResponse(t_player, serverEvent));
                }
            }
        }
        return response;
    }

    private static TexasHoldEmPlayer getTexasPlayer(Player player){
        return playerTexasMap.get(player);
    }

}
