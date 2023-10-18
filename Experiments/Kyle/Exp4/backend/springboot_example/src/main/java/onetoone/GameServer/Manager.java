package onetoone.GameServer;

import onetoone.Events.ServerEvent;
import onetoone.GoFish.GoFishManager;
import onetoone.PlayerClasses.Player;
import onetoone.Responses.Response;
import onetoone.TexasHoldEm.TexasHoldEmManager;

import java.util.*;

public class Manager {

    public static Response getResponse(Player player, ServerEvent serverEvent){
        switch(serverEvent.getGame()){
            case "texasholdem":
                return TexasHoldEmManager.getResponse(player, serverEvent.getAction());
            case "gofish":
                return GoFishManager.getResponse(player, serverEvent.getAction());
            default:
                return new Response();
        }
    }
}
