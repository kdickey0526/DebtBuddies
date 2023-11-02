package onetoone.GameServer;

import onetoone.GameServer.Communication.ServerEvent;
import onetoone.GameServer.Games.GameManager;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEm;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmUser;
import onetoone.GameServer.PlayerClasses.User;

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
