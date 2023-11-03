package debtbuddies.GameServer;

import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.Games.GameManager;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEm;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmUser;
import debtbuddies.GameServer.Games.War.War;
import debtbuddies.GameServer.PlayerClasses.CardUser;
import debtbuddies.GameServer.PlayerClasses.User;

public class Manager {

    private static GameManager<TexasHoldEmUser, TexasHoldEm> texasManager = new GameManager<>(new TexasHoldEm());

    private static GameManager<CardUser, War> warManager = new GameManager<CardUser, War>(new War());

    public static void getResponse(String game, User user, ServerEvent serverEvent){
        switch(game){
            case "texasholdem":
                texasManager.getResponse(user, serverEvent);
                break;
            case "war":
                warManager.getResponse(user, serverEvent);
            case "gofish":
                //return GoFishManager.getResponse(player, serverEvent.getAction());
                break;
            default:
                return;
        }
    }
}
