package onetoone.GameServer;

import com.google.gson.Gson;
import onetoone.PlayerClasses.Player;
import onetoone.PlayerClasses.TexasHoldEmPlayer;

import java.lang.reflect.Type;
import java.util.*;

public abstract class Game<T> {

    protected int gameId;
    protected int running;
    protected int num_players;
    protected List<T> players;
    protected Gson gson = new Gson();

    public Game(List<T> players, int gameId){
        this.gameId = gameId;
        this.players = players;
        num_players = players.size();
        running = 0;
    }

    public Game(int gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
        num_players = 0;
        running = 0;
    }

    protected int getGameId(){
        return gameId;
    }

    protected void addPlayer(T player){
        players.add(player);
    }

    protected List<Player> getAllPlayers(){
        List<Player> all_players = new ArrayList<>();
        for(T t_player : players){
            all_players.add((Player) t_player);
        }
        return all_players;
    }

    protected abstract void initializeGame();

}
