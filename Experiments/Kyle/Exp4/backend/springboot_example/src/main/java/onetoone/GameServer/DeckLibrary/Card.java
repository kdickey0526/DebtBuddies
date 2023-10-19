package onetoone.GameServer.DeckLibrary;

public class Card {

    public final Suit suit;
    public final int rank;

    public Card(Suit suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){
        return suit;
    }

    public int getRank(){
        return rank;
    }

    @Override
    public String toString() {
        String rank_string;
        switch(rank){
            case 11:
                rank_string = "JACK";
                break;
            case 12:
                rank_string = "QUEEN";
                break;
            case 13:
                rank_string = "KING";
                break;
            case 14:
                rank_string = "ACE";
                break;
            default:
                rank_string = Integer.toString(rank);
        }
        return rank_string + " of " + suit;
    }

}

