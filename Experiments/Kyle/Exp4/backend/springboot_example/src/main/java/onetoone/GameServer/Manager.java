package onetoone.GameServer;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.User;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmManager;

public class Manager {

    private static TexasHoldEmManager texasManager = new TexasHoldEmManager();

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
