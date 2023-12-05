package debtbuddies.GameServer.Games;

import com.google.gson.Gson;
import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.PlayerClasses.Group;
import debtbuddies.GameServer.PlayerClasses.User;

import java.util.*;

public abstract class Game<T> {
    protected int gameId;
    protected int running;
    protected int num_users;
    protected int queue_size;

    protected List<User> users;
    protected List<T> players = new ArrayList<>();
    protected Map< User , T > userPlayerMap = new Hashtable<>();

    protected Map< T , User > playerUserMap = new Hashtable<>();

    public Game(List<User> users, int gameId){
        this.users = users;
        this.gameId = gameId;
        num_users = users.size();
        running = 0;
    }

    public Game(){}

    public abstract void initializeGame();

    protected abstract void getResponse(User user, ServerEvent serverEvent);

    protected abstract Game<T> getNewGame(Group queue, int gameId);

    protected abstract T getNewUser(User user);

    public int getGameId(){
        return gameId;
    }

    public int getQueueSize(){
        return queue_size;
    }

    public void convertUsers(){
        players.clear();
        userPlayerMap.clear();
        playerUserMap.clear();
        for(User user : users){
            T player = getNewUser(user);
            players.add(player);
            userPlayerMap.put(user, player);
            playerUserMap.put(player, user);
        }
    }

}
