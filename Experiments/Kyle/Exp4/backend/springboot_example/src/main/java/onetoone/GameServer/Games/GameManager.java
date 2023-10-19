package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public abstract class GameManager<T , K extends GameInterface<T>> {

    protected int GameId = 0;

    protected List<T> Queue = new ArrayList<>();

    protected List<K> Servers = new ArrayList<>();

    protected Map<User, T > userPlayerMap = new Hashtable<>();

    protected Map < String, Integer > usernameGameIdMap = new Hashtable<>();

    protected Map < Integer , K > gameIdServerMap = new Hashtable<>();

    protected Map < K , Integer > serverGameIdMap = new Hashtable<>();

    public GameManager(){}

    protected abstract T getNewUser(User user);

    protected abstract K getNewGame(List<T> queue, Integer gameId);

    public Response getResponse(User user, ServerEvent serverEvent){
        Response response = new Response();

        if(!usernameGameIdMap.containsKey(user.toString()) && Objects.equals(serverEvent.getAction(), "joinQueue")){
            T new_player = getNewUser(user);
            userPlayerMap.put(user, new_player);
            Queue.add(new_player);
            if(Queue.size() == 3){
                K new_game = getNewGame(Queue, ++GameId);
                for(T temp_player : Queue) {
                    usernameGameIdMap.put(temp_player.toString(), GameId);
                }
                Servers.add(new_game);
                serverGameIdMap.put(new_game, GameId);
                gameIdServerMap.put(GameId, new_game);
                GameEvent gameEvent = new GameEvent("joinGame", "{\"gameid\":"+GameId+"}");
                response.addMessage(new Message(getAllUsers(Queue), gameEvent.toString()));
                Queue.clear();
            }else{
                GameEvent gameEvent = new GameEvent("queue");
                response.addMessage(new Message(user, gameEvent.toString()));
            }
        }else{
            for(K server : Servers){
                if(Objects.equals(usernameGameIdMap.get(user.toString()), serverGameIdMap.get(server))){
                    response.addResponse(server.getResponse(getUserPlayer(user), serverEvent));
                    break;
                }
            }
        }
        return response;
    }

    public T getUserPlayer(User user){
        return userPlayerMap.get(user);
    }

    public List<User> getAllUsers(List<T> players){
        List<User> temp_users = new ArrayList<>();
        for(T player : players){
            temp_users.add((User) player);
        }
        return temp_users;
    }

    private T getPlayer(User user){
        return userPlayerMap.get(user);
    }

}
