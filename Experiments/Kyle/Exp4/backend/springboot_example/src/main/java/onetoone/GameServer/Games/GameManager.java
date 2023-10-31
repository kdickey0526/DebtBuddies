package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.PlayerClasses.Group;
import onetoone.GameServer.PlayerClasses.User;
import onetoone.GameServer.Games.*;

import java.util.*;

public class GameManager<T , K extends GameInterface<T, K>> {

    protected int GameId = 0;

    protected int GroupId = 0;

    protected Group Queue = new Group(GroupId++);

    protected List<Group> Lobbies = new ArrayList<>();

    protected Map< User , Group > userLobbyMap = new Hashtable<>();
    protected Map < Integer , Group > groupIdLobbyMap = new Hashtable<>();
    protected Map < User , Integer > userGameIdMap = new Hashtable<>();
    protected Map < Integer , K > gameIdServerMap = new Hashtable<>();

    protected Map < User , T > userPlayerMap = new Hashtable<>();
    protected K dummyInstance;

    public GameManager(K dummyInstance){
        this.dummyInstance = dummyInstance;
    }

    public void getResponse(User user, ServerEvent serverEvent){
        String action = serverEvent.getAction();
        if(userGameIdMap.containsKey(user)){
            K server = gameIdServerMap.get(userGameIdMap.get(user));
            server.getResponse(userPlayerMap.get(user), serverEvent);
        }else{
            switch(action){
                case "joinQueue":
                    Queue.add(user);
                    userLobbyMap.put(user, Queue);
                    break;
                case "joinLobby":
                    if(groupIdLobbyMap.containsKey(serverEvent.getValue())){
                        Group current_lobby = groupIdLobbyMap.get(serverEvent.getValue());
                        current_lobby.add(user);
                        userLobbyMap.put(user, current_lobby);
                    }
                    break;
                case "createLobby":
                    Group new_lobby = new Group(user, GroupId++);
                    Lobbies.add(new_lobby);
                    userLobbyMap.put(user, new_lobby);
                    groupIdLobbyMap.put(new_lobby.getGroupId(), new_lobby);
                    break;
                case "start":
                    Group current_lobby = userLobbyMap.get(user);
                    K new_game = dummyInstance.getNewGame(current_lobby, ++GameId);
                    for(User current_user : current_lobby.getUsers()){
                        userGameIdMap.put(current_user, GameId);
                    }
                    break;
                default:
                    return;
            }
        }
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
