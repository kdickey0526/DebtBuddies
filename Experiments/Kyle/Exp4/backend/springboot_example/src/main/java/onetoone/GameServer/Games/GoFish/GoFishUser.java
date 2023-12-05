package onetoone.GameServer.Games.GoFish;

import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class GoFishUser extends User {

    private List<Card> hand;

    public GoFishUser(User user){
        super(user);
    }

    public GoFishUser(){
        super();
    }

}
