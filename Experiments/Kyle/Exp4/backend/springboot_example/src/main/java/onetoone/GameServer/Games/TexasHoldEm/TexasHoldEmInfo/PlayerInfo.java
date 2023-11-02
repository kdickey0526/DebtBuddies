package onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEm;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmUser;

import java.util.List;

public class PlayerInfo {

    private int balance;

    private int money_in_pot;

    private int money_in_pot_round;

    public PlayerInfo(int balance, int money_in_pot, int money_in_pot_round){
        this.balance = balance;
        this.money_in_pot = money_in_pot;
        this.money_in_pot_round = money_in_pot_round;
    }

}
