package onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

import onetoone.GameServer.DeckLibrary.Card;

import java.util.*;

public class EndInfo {

    public String winner;

    public String winning_hand_type;

    public List<Card> winning_hand;

    public EndInfo(String winner, String winning_hand_type, List<Card> winning_hand){
        this.winner = winner;
        this.winning_hand_type = winning_hand_type;
        this.winning_hand = winning_hand;
    }

}
