package debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

import debtbuddies.GameServer.DeckLibrary.Card;

import java.util.*;

public class EndInfo {

    public String winner;

    public String winning_hand_type;

    public List<Card> winning_hand;

    public int payout;

    public EndInfo(String winner, String winning_hand_type, List<Card> winning_hand, int payout){
        this.winner = winner;
        this.winning_hand_type = winning_hand_type;
        this.winning_hand = winning_hand;
        this.payout = payout;
    }

}
