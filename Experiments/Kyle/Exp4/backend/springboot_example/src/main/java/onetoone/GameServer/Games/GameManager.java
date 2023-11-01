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

    protected int LobbyId = 0;

    protected Group Queue = new Group(LobbyId++);

    protected List<Group> Lobbies = new ArrayList<>();

    protected Map< User , Group > userLobbyMap = new Hashtable<>();
    protected Map < Integer , Group > lobbyIdLobbyMap = new Hashtable<>();
    protected Map < User , Integer > userGameIdMap = new Hashtable<>();
    protected Map < Integer , K > gameIdServerMap = new Hashtable<>();
    protected Map < User, T > userPlayerMap = new Hashtable<>();
    protected K dummyInstance;

    public GameManager(K dummyInstance){
        this.dummyInstance = dummyInstance;
        lobbyIdLobbyMap.put(0, Queue);
    }

    public void getResponse(User user, ServerEvent serverEvent){
        String action = serverEvent.getAction();
        if(inGame(user)){
            gameAction(user, serverEvent);
        }else{
            switch(action){
                case "joinQueue":
                    joinQueue(user);
                    Response.addMessage(new Message(user, "joined queue"));
                    break;
                case "joinLobby":
                    joinLobby(user, serverEvent.getValue());
                    break;
                case "createLobby":
                    createLobby(user);
                    break;
                case "leaveLobby":
                    leaveLobby(user);
                    break;
                case "start":
                    startGame(user);
                    break;
                default:
                    return;
            }
        }
    }

    public void gameAction(User user, ServerEvent serverEvent){
        K server = gameIdServerMap.get(userGameIdMap.get(user));
        server.getResponse(userPlayerMap.get(user), serverEvent);
    }

    public void joinQueue(User user){
        Queue.add(user);
        userLobbyMap.put(user, Queue);
        if(Queue.getNumUsers() == dummyInstance.getQueueSize()){
            startGame(user);
        }
    }

    public boolean inGame(User user){
        return userGameIdMap.containsKey(user);
    }

    public void joinLobby(User user, int lobbyId){
        if(!lobbyIdLobbyMap.containsKey(lobbyId)){ return; }

        Group current_lobby = lobbyIdLobbyMap.get(lobbyId);
        current_lobby.add(user);
        userLobbyMap.put(user, current_lobby);
    }

    public void leaveLobby(User user){
        Group current_lobby = userLobbyMap.get(user);
        current_lobby.remove(user);
        userLobbyMap.remove(user);
        userGameIdMap.remove(user);
        if(current_lobby.getNumUsers() == 0 && current_lobby.getGroupId() != 0){
            lobbyIdLobbyMap.remove(current_lobby.getGroupId());
        }
    }

    public void createLobby(User user){
        Group new_lobby = new Group(user, LobbyId++);
        Lobbies.add(new_lobby);
        userLobbyMap.put(user, new_lobby);
        lobbyIdLobbyMap.put(new_lobby.getGroupId(), new_lobby);
    }

    public void startGame(User user){

        Group current_lobby = userLobbyMap.get(user);

        if(current_lobby.isQueue()){
            leaveLobby(user);
            createLobby(user);
            current_lobby = userLobbyMap.get(user);
            for(User temp_user : Queue.getUsers()){
                leaveLobby(temp_user);
                joinLobby(temp_user, userLobbyMap.get(user).getGroupId());
            }
        }

        K new_game = dummyInstance.getNewGame(current_lobby, ++GameId);
        gameIdServerMap.put(GameId, new_game);

        for(User current_user : current_lobby.getUsers()){
            userGameIdMap.put(current_user, GameId);
        }
    }

}
