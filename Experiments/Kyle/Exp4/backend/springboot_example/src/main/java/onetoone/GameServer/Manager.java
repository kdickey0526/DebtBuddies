package onetoone.GameServer;

import java.util.Objects;

public class Manager {

    private static TexasManager texasManager = new TexasManager();

    public static void sendToQueue(Player player, String game){
        if(Objects.equals(game, "texasholdem")){
            texasManager.addToQueue(player);
        }
    }

}
