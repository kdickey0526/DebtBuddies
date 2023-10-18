package onetoone.PlayerClasses;

import onetoone.DeckLibrary.Card;

import java.util.*;

public class GoFishPlayer extends Player{

    private List<Card> hand;



    public GoFishPlayer(Player player){
        super(player);
    }

    public GoFishPlayer(){
        super();
    }

}
