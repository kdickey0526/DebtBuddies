package debtbuddies.GameServer.PlayerClasses;

import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Deck;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CardUser extends User {

    private List<Card> hand = new ArrayList<>();
    private List<Card> discard = new ArrayList<>();

    public CardUser(String username){
        super(username);
    }

    public CardUser(){
        super();
    }

    public void giveCard(Card card){
        discard.add(card);
    }

    public void draw(Deck deck){
        hand.add(deck.draw());
    }

    public List<Card> getHand(){
        return hand;
    }

    public Card play(){
        return hand.remove(0);
    }

    public int getNumCards(){
        return hand.size() + discard.size();
    }

    public void clearInventory(){
        hand = new ArrayList<>();
        discard = new ArrayList<>();
    }

    public boolean checkHand(){
        if(hand.isEmpty() && !discard.isEmpty()){
            for(int i = 0; i < discard.size(); i++){
                hand.add(discard.remove(0));
            }
            shuffle();
        }
        return !hand.isEmpty();
    }

    public void shuffle(){
        Collections.shuffle(hand);
    }

}
