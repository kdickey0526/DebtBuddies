package cardgames;

/**
 *  Basic class to represent a card object.
 * 
 *  A card has a suit and value.
 *  
 *  Suits: Heart, Diamond, Club, Spade
 *  Rank/value: A-K, represented as an integer from 1-13.
 *  
 * @author Kevin
 *
 */
public class Card {
	
	/**
	 * Holds the value of the card, ace's low. 
	 * 
	 * 1 -- Ace		5 -- Five 	  9  -- Nine	13 -- King
	 * 2 -- Two		6 -- Six	  10 -- Ten
	 * 3 -- Three	7 -- Seven	  11 -- Jack
	 * 4 -- Four	8 -- Eight	  12 -- Queen
	 */
	private final int rank;
	
	/**
	 * Holds the suit of the card.
	 */
	private final Suit suit;
	
	/**
	 * The available suits of the cards.
	 * 
	 * @author Kevin
	 */
	public enum Suit {
		HEART,
		SPADE,
		DIAMOND,
		CLUB
	}
	
	/**
	 * Generates a specified card.
	 * @param rank -- The rank/value to give the new card.
	 * @param suit -- The suit to give the new card.
	 */
	public Card(int rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * Returns the suit of the current card object as a Suit enum.
	 * 
	 * @return the suit
	 */
	public Suit getSuit() {
		return this.suit;
	}
	
	/**
	 * Returns the rank/value of the current card object as an integer.
	 * 
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}
	
	public String toString() {
		return rank + ":" + suit;
	}
}
