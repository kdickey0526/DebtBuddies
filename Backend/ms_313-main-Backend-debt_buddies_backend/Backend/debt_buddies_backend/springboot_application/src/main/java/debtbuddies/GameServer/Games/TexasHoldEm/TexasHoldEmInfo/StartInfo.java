package debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

import debtbuddies.GameServer.DeckLibrary.Card;

import java.util.*;

public class StartInfo {

    private List<Card> hand;

    private String next;

    private int pot;

    private int ante;

    public StartInfo(List<Card> hand, String next, int pot, int ante){
        this.hand = hand;
        this.next = next;
        this.pot = pot;
        this.ante = ante;
    }

}
