package debtbuddies;

import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Suit;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEm;
import io.swagger.models.auth.In;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandsTest {

    TexasHoldEm game = new TexasHoldEm();

    List<Card> cards;

    @Test
    public void CountTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 6));
        cards.add(new Card(Suit.CLUBS, 6));
        cards.add(new Card(Suit.DIAMONDS, 4));
        cards.add(new Card(Suit.CLUBS, 4));
        cards.add(new Card(Suit.HEARTS, 4));

        Map<Integer, Integer> nums = game.countNumbers(cards);

        assertTrue(true);
    }

    @Test
    public void RoyalFlushTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 10));
        cards.add(new Card(Suit.DIAMONDS, 11));
        cards.add(new Card(Suit.DIAMONDS, 12));
        cards.add(new Card(Suit.DIAMONDS, 13));
        cards.add(new Card(Suit.DIAMONDS, 14));

        assertTrue(game.royalFlush(cards));
    }

    @Test
    public void StraightFlushTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, 4));
        cards.add(new Card(Suit.CLUBS, 5));
        cards.add(new Card(Suit.CLUBS, 6));
        cards.add(new Card(Suit.CLUBS, 7));
        cards.add(new Card(Suit.CLUBS, 8));

        assertTrue(game.straightFlush(cards));
    }

    @Test
    public void FourOfAKindTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 5));
        cards.add(new Card(Suit.HEARTS, 5));
        cards.add(new Card(Suit.CLUBS, 5));
        cards.add(new Card(Suit.SPADES, 5));

        assertTrue(game.fourOfAKind(cards));
    }

    @Test
    public void FullHouseTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 8));
        cards.add(new Card(Suit.CLUBS, 8));
        cards.add(new Card(Suit.DIAMONDS, 5));
        cards.add(new Card(Suit.CLUBS, 5));
        cards.add(new Card(Suit.HEARTS, 5));

        assertTrue(game.fullHouse(cards));
    }

    @Test
    public void FlushTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 5));
        cards.add(new Card(Suit.DIAMONDS, 2));
        cards.add(new Card(Suit.DIAMONDS, 3));
        cards.add(new Card(Suit.DIAMONDS, 9));
        cards.add(new Card(Suit.DIAMONDS, 12));

        assertTrue(game.flush(cards));
    }

    @Test
    public void StraightTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 6));
        cards.add(new Card(Suit.DIAMONDS, 7));
        cards.add(new Card(Suit.DIAMONDS, 8));
        cards.add(new Card(Suit.DIAMONDS, 9));
        cards.add(new Card(Suit.DIAMONDS, 10));

        assertTrue(game.straight(cards));
    }

    @Test
    public void ThreeOFAKindTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 7));
        cards.add(new Card(Suit.CLUBS, 7));
        cards.add(new Card(Suit.HEARTS, 7));

        assertTrue(game.threeOfAKind(cards));
    }

    @Test
    public void TwoPairTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 5));
        cards.add(new Card(Suit.CLUBS, 5));
        cards.add(new Card(Suit.DIAMONDS, 9));
        cards.add(new Card(Suit.HEARTS, 9));

        assertTrue(game.twoPair(cards));
    }

    @Test
    public void PairTest(){
        cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, 8));
        cards.add(new Card(Suit.HEARTS, 8));

        assertTrue(game.pair(cards));
    }

}
