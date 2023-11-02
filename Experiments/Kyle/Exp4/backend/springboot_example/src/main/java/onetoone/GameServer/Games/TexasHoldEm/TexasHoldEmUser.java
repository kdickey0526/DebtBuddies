package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.DeckLibrary.Deck;
import onetoone.GameServer.PlayerClasses.User;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmUser extends User {
    private List<Card> hand = new ArrayList<>();

    private int bet;

    private int ante;

    private boolean fold;

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

    public List<Card> getHand(){
        return hand;
    }

    public void clearInventory(){
        this.bet = 0;
        this.ante = 0;
        this.hand = new ArrayList<>();
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
