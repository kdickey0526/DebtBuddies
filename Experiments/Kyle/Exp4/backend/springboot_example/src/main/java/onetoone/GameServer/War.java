package onetoone.GameServer;

import java.util.List;
import java.util.ArrayList;

public class War {

    private final CardPlayer player1;
    private final CardPlayer player2;

    private Deck deck;

    public War(CardPlayer player1, CardPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
        deck = new Deck();
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < 2; j++){
                if(j == 0){
                    player1.draw(deck);
                }else{
                    player2.draw(deck);
                }
            }
        }
    }

    public void playGame(){
        int count = 0;
        while(player1.getNumCards() > 0){
            count++;
            System.out.println("\nTurn: " + Integer.toString(count) + "\n");
            play_turn();
        }
    }

    public void play_turn(){

        System.out.println("----------------------");

        List<Card> p1cards = new ArrayList<>();
        List<Card> p2cards = new ArrayList<>();

        p1cards.add(player1.play());
        player1.checkHand();
        System.out.println(player1.toString() + " played " + p1cards.get(0).toString() + "\n");
        p2cards.add(player2.play());
        player2.checkHand();
        System.out.println(player2.toString() + " played " + p2cards.get(0).toString() + "\n");

        int i = 0;
        int round;

        while(p1cards.get(i).getRank() == p2cards.get(i).getRank()){
            round = ((i + 2) / 2);
            System.out.println("War round " + Integer.toString(round) + "\n");
            if(player1.getNumCards() < 2){
                endgame(player2, player1);
                return;
            }else if(player2.getNumCards() < 2){
                endgame(player1, player2);
                return;
            }
            p1cards.add(player1.play());
            player1.checkHand();
            p1cards.add(player1.play());
            player1.checkHand();
            p2cards.add(player2.play());
            player2.checkHand();
            p2cards.add(player2.play());
            player2.checkHand();
            i += 2;
            System.out.println(player1.toString() + " played " + p1cards.get(i).toString());
            System.out.println(player2.toString() + " played " + p2cards.get(i).toString());
        }

        CardPlayer winner = (p1cards.get(i).getRank() > p2cards.get(i).getRank()) ? player1 : player2;

        System.out.println(winner.toString() + " won the round\n");

        payout(winner, p1cards, p2cards);

        System.out.println(player1.toString() + " cards remaining: " + player1.getNumCards());
        System.out.println(player2.toString() + " cards remaining: " + player2.getNumCards());
        player1.checkHand();
        player2.checkHand();

        if(player1.getNumCards() == 0){
            endgame(player2, player1);
        }else if(player2.getNumCards() == 0){
            endgame(player1, player2);
        }

    }

    private void payout(CardPlayer winner, List<Card> d1, List<Card> d2){
        for (Card card : d1) {
            winner.giveCard(card);
        }
        for (Card card : d2) {
            winner.giveCard(card);
        }
    }

    public void endgame(CardPlayer winner, CardPlayer loser){
        winner.clearInventory();
        loser.clearInventory();
        System.out.println(winner.toString() + " beat " + loser.toString() + "\n");
    }

}
