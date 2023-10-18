package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEm;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmPlayer;
import onetoone.GameServer.PlayerClasses.Player;

import java.util.*;

public abstract class GameManager<T, K> {

    protected int GameId = 0;

    protected List<T> Queue = new ArrayList<>();

    protected List<K> Servers = new ArrayList<>();

    protected Map<Player, T > userPlayerMap = new Hashtable<>();

    protected Map < String, Integer > usernameGameIdMap = new Hashtable<>();

    protected Map < Integer , K > gameIdServerMap = new Hashtable<>();

    protected Map < K , Integer > serverGameIdMap = new Hashtable<>();

    protected abstract T getNewPlayer(Player player);

    protected abstract K getNewGame(List<T> queue, Integer gameId);

    protected abstract Response getGameResponse(T player, ServerEvent serverEvent);

    public Response getResponse(Player player, ServerEvent serverEvent){
        Response response = new Response();

        if(!usernameGameIdMap.containsKey(player.toString()) && Objects.equals(serverEvent.getAction(), "joinQueue")){
            T new_player = getNewPlayer(player);
            userPlayerMap.put(player, new_player);
            Queue.add(new_player);
            if(Queue.size() == 3){
                K new_game = getNewGame(Queue, ++GameId);
                for(T temp_player : Queue) {
                    usernameGameIdMap.put(temp_player.toString(), GameId);
                }
                Servers.add(new_game);
                gameIdServerMap.put(GameId, new_game);
                GameEvent gameEvent = new GameEvent("joinGame", "{\"event\":\"join\",\"value\":{\"gameid\":"+GameId+"}}");
                response.addMessage(new Message(getAllPlayers(Queue), gameEvent.toString()));
                Queue.clear();
            }else{
                GameEvent gameEvent = new GameEvent("queue");
                response.addMessage(new Message(player, gameEvent.toString()));
            }
        }else{
            for(K server : Servers){
                if(Objects.equals(usernameGameIdMap.get(player.toString()), serverGameIdMap.get(server))){
                    T t_player = getUserPlayer(player);
                    response.addResponse(getGameResponse(t_player, serverEvent));
                }
            }
        }
        return response;
    }

    public T getUserPlayer(Player player){
        return userPlayerMap.get(player);
    }

    public List<Player> getAllPlayers(List<T> players){
        List<Player> temp_players = new ArrayList<>();
        for(T player : players){
            temp_players.add((Player) player);
        }
        return temp_players;
    }

    private T getPlayer(Player player){
        return userPlayerMap.get(player);
    }

}
