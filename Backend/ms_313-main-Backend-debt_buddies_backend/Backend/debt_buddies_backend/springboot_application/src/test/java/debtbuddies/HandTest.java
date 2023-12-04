package debtbuddies;

import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Suit;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEm;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandTest {

    List<Card> cards = new ArrayList<>();

    TexasHoldEm game = new TexasHoldEm();

    @Test
    public void royalstraightTest() {
        cards.add(new Card(Suit.DIAMONDS, 10));
        cards.add(new Card(Suit.DIAMONDS, 11));
        cards.add(new Card(Suit.DIAMONDS, 12));
        cards.add(new Card(Suit.DIAMONDS, 13));
        cards.add(new Card(Suit.DIAMONDS, 14));

        assertTrue(game.straightFlush(cards));
    }

}
