package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Games.GameManager;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEm;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmPlayer;
import onetoone.GameServer.PlayerClasses.Player;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class GoFishManager extends GameManager<GoFishPlayer, GoFish > {

    public GoFishManager(){
        super();
    }

    @Override
    protected GoFishPlayer getNewPlayer(Player player) {
        return new GoFishPlayer(player);
    }

    @Override
    protected GoFish getNewGame(List<GoFishPlayer> queue, Integer gameId) {
        return new GoFish(queue, gameId);
    }
}
