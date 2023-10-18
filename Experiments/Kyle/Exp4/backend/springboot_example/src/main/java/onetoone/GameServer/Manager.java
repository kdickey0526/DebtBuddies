package onetoone.GameServer;

import onetoone.Events.Event;
import onetoone.GoFish.GoFishManager;
import onetoone.PlayerClasses.Player;
import onetoone.Responses.Response;
import onetoone.TexasHoldEm.TexasHoldEmManager;

import java.util.Objects;

public class Manager {

    private static TexasHoldEmManager texasManager = new TexasHoldEmManager();

    private static GoFishManager fishManager = new GoFishManager();

    public static Response getResponse(Player player, String game, Event event){
        if(Objects.equals(game, "texasholdem")){
            return texasManager.getResponse(player, event.getAction());
        }else if(Objects.equals(game, "gofish")){
            return fishManager.getResponse(player, event.getAction());
        }
        return new Response();
    }

}
