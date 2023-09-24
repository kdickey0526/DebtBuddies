package cardgames;

import java.util.ArrayList;
import java.util.Arrays;

import cardgames.Card.Suit;

public class Deck {

	private static final Suit[] suits = {Suit.HEART, Suit.SPADE, Suit.DIAMOND, Suit.CLUB};
	
	private ArrayList<Card> deck;
	
	/**
	 * Generates a sorted deck of 52 cards.
	 */
	public Deck() {
		this.deck = new ArrayList<Card>();
		for (int i = 1; i < 14; i++) {	// iterate through all ranks
			for (int j = 0; j < 4; j++) { // iterate through all suits
				this.deck.add(new Card(i, suits[j]));
			}
		}
	}
	
	public void shuffle() {
		// TODO
	}
	
	public void printDeck() {
		System.out.println(this.deck);
	}
}
