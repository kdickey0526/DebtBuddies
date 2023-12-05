package debtbuddies;

import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Deck;
import debtbuddies.GameServer.DeckLibrary.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeckTest {

    Deck deck;

    Card card;

    @Test
    public void StringTest(){
        card = new Card(Suit.DIAMONDS, 8);

        assertEquals("8 of DIAMONDS", card.toString());

        card = new Card(Suit.DIAMONDS, 11);

        assertEquals("JACK of DIAMONDS", card.toString());

        card = new Card(Suit.DIAMONDS, 12);

        assertEquals("QUEEN of DIAMONDS", card.toString());

        card = new Card(Suit.DIAMONDS, 13);

        assertEquals("KING of DIAMONDS", card.toString());

        card = new Card(Suit.DIAMONDS, 14);

        assertEquals("ACE of DIAMONDS", card.toString());
    }

    @Test
    public void InitializeDeckTest(){
        deck = new Deck();

        assertEquals(52, deck.getCards().size());

        Deck.CardManager.sortCards(deck.getCards());

        assertEquals(2, deck.getCard(0).getRank());

        Card newcard = deck.draw();

        assertFalse(Deck.CardManager.contains(deck.getCards(), newcard.getSuit(), newcard.getRank()));

        List<Card> cards = new ArrayList<>();

        cards.add(new Card(Suit.DIAMONDS, 9));

        assertFalse(Deck.CardManager.contains(cards, 10));

        deck.reset();

        deck.toString();
    }

}
