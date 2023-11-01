package onetoone.GameServer.Communication.TexasHoldEmInfo;

import onetoone.GameServer.DeckLibrary.Card;

import java.util.List;

public class PlayerInfo {

    private final List<Card> hand;

    private int balance;

    private int money_in_pot;

    private int money_in_pot_round;

    public PlayerInfo(List<Card> hand){
        this.hand = hand;
    }

    public PlayerInfo(List<Card> hand, int balance, int money_in_pot, int money_in_pot_round){
        this.hand = hand;
        this.balance = balance;
        this.money_in_pot = money_in_pot;
        this.money_in_pot_round = money_in_pot_round;
    }

}
