package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Games.GameManager;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class GoFishManager extends GameManager<GoFishUser, GoFish > {

    public GoFishManager(){
        super();
    }

    @Override
    protected GoFishUser getNewUser(User user) {
        return new GoFishUser(user);
    }

    @Override
    protected GoFish getNewGame(List<GoFishUser> queue, Integer gameId) {
        return new GoFish(queue, gameId);
    }
}
