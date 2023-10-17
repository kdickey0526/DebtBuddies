package onetoone.GameServer;

import onetoone.GameServer.Card;
import onetoone.GameServer.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck(){
        initializeDeck();
    }

    private void initializeDeck(){
        for(Suit suit : Suit.values()){
            for(int rank = 2; rank <= 14; rank++){
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public List<Card> getCards(){
        return cards;
    }

    public Card draw(){
        return cards.remove(0);
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void reset(){
        cards = new ArrayList<>();
        initializeDeck();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < cards.size(); i++){
            s.append(cards.get(i).toString());
            if(i != cards.size() - 1){
                s.append("\n");
            }
        }
        return s.toString();
    }

    public static class CardManager{
        public static void sortCards(List<Card> cards){
            cards.sort(Comparator.comparingInt(Card::getRank));
        }

        public static boolean contains(List<Card> cards, int num){
            for (Card card : cards) {
                if (card.getRank() == num) {
                    return true;
                }
            }
            return false;
        }

        public static boolean contains(List<Card> cards, Suit suit, int num){
            for (Card card : cards) {
                if (card.getSuit() == suit && card.getRank() == num) {
                    return true;
                }
            }
            return false;
        }
    }

}
