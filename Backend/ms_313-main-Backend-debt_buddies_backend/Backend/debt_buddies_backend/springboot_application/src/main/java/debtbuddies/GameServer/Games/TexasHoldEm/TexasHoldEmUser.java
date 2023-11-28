package debtbuddies.GameServer.Games.TexasHoldEm;

import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Deck;
import debtbuddies.GameServer.PlayerClasses.User;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmUser extends User {
    private List<Card> hand = new ArrayList<>();

    private int bet;

    private int ante;

    private boolean fold;

    private PokerHands high_hand = PokerHands.LOW;

    private List<Card> high_hand_cards = new ArrayList<>();

    public TexasHoldEmUser(User user){
        super(user.toString());
        bet = 0;
        fold = false;
    }

    public Card draw(Deck deck){
        Card pick = deck.draw();
        hand.add(pick);
        return pick;
    }

    public void addHigh_hand_card(Card card){
        high_hand_cards.add(card);
    }

    public List<Card> getHigh_hand_cards(){
        return high_hand_cards;
    }

    public void setHigh_hand(PokerHands hh){
        high_hand = hh;
    }

    public PokerHands getHigh_hand(){
        return high_hand;
    }

    public List<Card> getHand(){
        return hand;
    }

    public void clearInventory(){
        this.bet = 0;
        this.ante = 0;
        this.hand = new ArrayList<>();
        high_hand = PokerHands.LOW;
        high_hand_cards = new ArrayList<>();
    }

    public void foldHand(){
        fold = true;
    }

    public boolean foldStatus(){
        return fold;
    }

    public int placeBet(int bet){
        this.balance -= bet;
        this.bet += bet;
        this.ante += bet;
        return bet;
    }

    public int getBet(){
        return bet;
    }

    public void setBet(int bet){
        this.bet = bet;
    }

    public int getAnte(){
        return ante;
    }

    public void setAnte(int ante){
        this.ante = ante;
    }

    public int cashout(int amount){
        balance += amount;
        return balance;
    }

}
