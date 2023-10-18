package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Games.GameManager;
import onetoone.GameServer.PlayerClasses.Player;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;

import java.util.*;

public class TexasHoldEmManager extends GameManager<TexasHoldEmPlayer, TexasHoldEm> {

    public TexasHoldEmManager(){
        super();
    }

    @Override
    protected TexasHoldEmPlayer getNewPlayer(Player player) {
        return new TexasHoldEmPlayer(player);
    }

    @Override
    protected TexasHoldEm getNewGame(List<TexasHoldEmPlayer> queue, Integer gameId) {
        return new TexasHoldEm(queue, gameId);
    }

    @Override
    protected Response getGameResponse(TexasHoldEmPlayer player, ServerEvent serverEvent) {
        return gameIdServerMap.get(usernameGameIdMap.get(player.toString())).getResponse(player, serverEvent);
    }
}