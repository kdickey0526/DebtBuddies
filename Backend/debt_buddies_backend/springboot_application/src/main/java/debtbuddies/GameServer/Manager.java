package debtbuddies.GameServer;

import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.Games.GameManager;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEm;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmUser;
import debtbuddies.GameServer.PlayerClasses.User;

public class Manager {

    private static GameManager<TexasHoldEmUser, TexasHoldEm> texasManager = new GameManager<>(new TexasHoldEm());

    public static void getResponse(String game, User user, ServerEvent serverEvent){
        switch(game){
            case "texasholdem":
                texasManager.getResponse(user, serverEvent);
                return;
            case "gofish":
                //return GoFishManager.getResponse(player, serverEvent.getAction());
                return;
            default:
                return;
        }
    }
}
