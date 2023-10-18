package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.PlayerClasses.Player;

import java.util.*;

public class GoFishPlayer extends Player {

    private List<Card> hand;

    public GoFishPlayer(Player player){
        super(player);
    }

    public GoFishPlayer(){
        super();
    }

}
