package onetoone.GameServer;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.User;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmManager;

public class Manager {

    private static TexasHoldEmManager texasManager = new TexasHoldEmManager();

    public static Response getResponse(String game, User user, ServerEvent serverEvent){
        switch(game){
            case "texasholdem":
                return texasManager.getResponse(user, serverEvent);
            case "gofish":
                //return GoFishManager.getResponse(player, serverEvent.getAction());
            default:
                return new Response();
        }
    }
}
