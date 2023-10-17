package onetoone.GameServer;

import java.util.*;

import static java.sql.Types.NULL;

public class TexasHoldEmManager {

    private static int GAMEID = 0;

    private static List<TexasHoldEmPlayer> Queue = new ArrayList<>();

    private static List<TexasHoldEm> Servers = new ArrayList<>();

    private static Map < Player , TexasHoldEmPlayer > playerTexasMap = new Hashtable<>();
    public TexasHoldEmManager(){

    }

    public Response getResponse(Player player, Action action){

        if(action.getGameid() == NULL){
            if(Objects.equals(action.getGame(), "texasholdem")){
                TexasHoldEmPlayer new_player = new TexasHoldEmPlayer(player);
                playerTexasMap.put(player, new_player);
                Queue.add(new_player);
                if(Queue.size() == 3){
                    TexasHoldEm new_game = new TexasHoldEm(Queue, ++GAMEID);
                    Servers.add(new_game);
                    return new_game.sendHands();
                }else{
                    Response re = new Response();
                    re.addPlayer(player);
                    re.setMessage("You have been added to queue");
                    return re;
                }
            }
        }else{
            for(TexasHoldEm server : Servers){
                if(action.getGameid() == server.getGameId()){
                    TexasHoldEmPlayer t_player = getTexasPlayer(player);
                    return server.getResponse(t_player, action.getMove());
                }
            }
        }
        List<Player> k = new ArrayList<>();
        k.add(player);
        List<String> m = new ArrayList<>();
        m.add("Error");
        return new Response(k, m);
    }

    private TexasHoldEmPlayer getTexasPlayer(Player player){
        return playerTexasMap.get(player);
    }

}
