package onetoone.GameServer;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Manager {

    private static TexasHoldEmManager texasManager = new TexasHoldEmManager();

    public static Response getResponse(Player player, Action action){
        if(Objects.equals(action.getGame(), "texasholdem")){
            return texasManager.getResponse(player, action);
        }
        List<Player> k = new ArrayList<>();
        k.add(player);
        return new Response(k, "Error");
    }

}
