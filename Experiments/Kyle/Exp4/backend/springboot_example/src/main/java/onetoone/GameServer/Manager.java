package onetoone.GameServer;

import onetoone.PlayerClasses.Player;
import onetoone.TexasHoldEm.TexasHoldEmManager;

import java.util.Objects;

public class Manager {

    private static TexasHoldEmManager texasManager = new TexasHoldEmManager();

    public static Response getResponse(Player player, String game, Event event){
        if(Objects.equals(game, "texasholdem")){
            return texasManager.getResponse(player, event.getAction());
        }
        return new Response();
    }

}
