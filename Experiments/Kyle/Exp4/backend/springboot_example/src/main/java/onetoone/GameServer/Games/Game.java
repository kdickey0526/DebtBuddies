package onetoone.GameServer.Games;

import com.google.gson.Gson;
import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.Group;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public abstract class Game<T> {
    protected int gameId;
    protected int running;
    protected int num_users;
    protected int queue_size;

    protected List<T> players;

    protected List<User> users;

    protected Map< User , T > userPlayerMap = new Hashtable<>();
    protected Gson gson = new Gson();

    public Game(List<User> users, int gameId, int queue_size){
        this.gameId = gameId;
        this.users = new ArrayList<>(users);
        this.queue_size = queue_size;
        num_users = users.size();
        running = 0;
    }

    public Game(int gameId){
        this.gameId = gameId;
        users = new ArrayList<>();
        num_users = 0;
        running = 0;
    }

    public Game(){

    }

    protected abstract void getResponse(T user, ServerEvent serverEvent);

    protected abstract Game<T> getNewGame(Group queue, int gameId);

    protected abstract T getNewUser(User user);
/*
    protected void addUser(T player){
        users.add(player);
    }
*/
    public int getGameId(){
        return gameId;
    }

    public int getQueueSize(){
        return queue_size;
    }

    public List<User> getAllUsers(){
        List<User> all_users = new ArrayList<>();
        for(T t_user : players){
            all_users.add((User) t_user);
        }
        return all_users;
    }

    protected abstract void initializeGame();

}
