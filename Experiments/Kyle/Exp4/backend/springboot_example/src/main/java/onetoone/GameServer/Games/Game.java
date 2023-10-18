package onetoone.GameServer.Games;

import com.google.gson.Gson;
import onetoone.GameServer.PlayerClasses.Player;

import java.util.*;

public abstract class Game<T> {

    protected int gameId;
    protected int running;
    protected int num_players;
    protected List<T> players;
    protected Gson gson = new Gson();

    public Game(List<T> players, int gameId){
        this.gameId = gameId;
        this.players = new ArrayList<>(players);
        num_players = players.size();
        running = 0;
    }

    public Game(int gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
        num_players = 0;
        running = 0;
    }

    protected void addPlayer(T player){
        players.add(player);
    }

    public int getGameId(){
        return gameId;
    }

    public List<Player> getAllPlayers(){
        List<Player> all_players = new ArrayList<>();
        for(T t_player : players){
            all_players.add((Player) t_player);
        }
        return all_players;
    }

    protected abstract void initializeGame();

}
