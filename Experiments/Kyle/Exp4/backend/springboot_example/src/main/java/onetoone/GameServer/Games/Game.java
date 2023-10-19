package onetoone.GameServer.Games;

import com.google.gson.Gson;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public abstract class Game<T> {

    protected int gameId;
    protected int running;
    protected int num_users;

    protected List<T> users;
    protected Gson gson = new Gson();

    public Game(List<T> users, int gameId){
        this.gameId = gameId;
        this.users = new ArrayList<>(users);
        num_users = users.size();
        running = 0;
    }

    public Game(int gameId){
        this.gameId = gameId;
        users = new ArrayList<>();
        num_users = 0;
        running = 0;
    }

    protected void addUser(T player){
        users.add(player);
    }

    public int getGameId(){
        return gameId;
    }

    public List<User> getAllUsers(){
        List<User> all_users = new ArrayList<>();
        for(T t_user : users){
            all_users.add((User) t_user);
        }
        return all_users;
    }

    protected abstract void initializeGame();

}
