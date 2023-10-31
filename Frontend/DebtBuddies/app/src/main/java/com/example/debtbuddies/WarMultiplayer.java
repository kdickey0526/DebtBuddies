package com.example.debtbuddies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.Random;
public class WarMultiplayer extends AppCompatActivity implements WebSocketListener {
    TextView tvPlayer1, tvPlayer2, whoWin;
    ImageView cardPlayer1, cardPlayer2;

    boolean gameOver;

    private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    ArrayList<Card> player1 = new ArrayList<Card>();
    ArrayList<Card> player2 = new ArrayList<Card>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String suit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);

        tvPlayer1 = findViewById(R.id.tv_player1);
        tvPlayer2 = findViewById(R.id.tv_player2);

        whoWin = findViewById(R.id.tv_text);

        cardPlayer1 = findViewById(R.id.id_player1);
        cardPlayer2 = findViewById(R.id.id_player2);

        ArrayList<Card> deck = new ArrayList<Card>();

        gameOver = false;


        for (int i = 1; i <= 4; i ++) {
            if (i == 1) {
                suit = "heart";
            } else if (i == 2) {
                suit = "diamond";
            } if (i == 3) {
                suit = "club";
            } else {
                suit = "spade";
            }
            for(int j = 2; j <= 14; j ++) {
                Card c = new Card();
                c.createCard(suit, j, suit + j);
                deck.add(c);
            }
        }
        int temp;
        Random r = new Random();
        for (int i = 52; i > 3; i --) { //change back to 26
            temp = r.nextInt(i);
            player1.add(deck.get(temp));
            deck.remove(temp);

        }
        player2 = deck;

        tvPlayer1.setText(String.valueOf(player2.size()));
        tvPlayer2.setText(String.valueOf(player1.size()));
    }

    public void onDealClicked (View view) {
        if (gameOver != true) {
            int player1val;
            int player2val;
            int image;
            String card = player1.get(0).getID();
            image = getResources().getIdentifier(card, "drawable", getPackageName());
            cardPlayer1.setImageResource(image);
            player1val = player1.get(0).value;


            card = player2.get(0).getID();
            image = getResources().getIdentifier(card, "drawable", getPackageName());
            cardPlayer2.setImageResource(image);
            player2val = player2.get(0).value;


            if (player1val > player2val) {  // 1 > 2
                player1.add(player1.get(0));
                player1.add(player2.get(0));
            } else if (player2val == player1val) {  // 1 = 2 war
                if (player1.size() <= 4 || player2.size() <= 4) {
                    gameOver();
                }
                else if (player1.get(4).value > player2.get(4).value) {
                    player1.add(player1.get(0));
                    player1.add(player2.get(0));
                    player1.add(player1.get(1));
                    player1.add(player2.get(1));
                    player1.add(player1.get(2));
                    player1.add(player2.get(2));
                    player1.add(player1.get(3));
                    player1.add(player2.get(3));
                    player1.add(player1.get(4));
                    player1.add(player2.get(4));
                } else {
                    player2.add(player1.get(0));
                    player2.add(player2.get(0));
                    player2.add(player1.get(1));
                    player2.add(player2.get(1));
                    player2.add(player1.get(2));
                    player2.add(player2.get(2));
                    player2.add(player1.get(3));
                    player2.add(player2.get(3));
                    player2.add(player1.get(4));
                    player2.add(player2.get(4));
                }
                if (gameOver == false) {
                    player1.remove(4);
                    player1.remove(3);
                    player1.remove(2);
                    player1.remove(1);
                    player2.remove(4);
                    player2.remove(3);
                    player2.remove(2);
                    player2.remove(1);
                }
            } else {    // 1 < 2
                player2.add(player1.get(0));
                player2.add(player2.get(0));
            }

            player1.remove(0);
            player2.remove(0);

            if (player1.size() == 0 || player2.size() == 0) {
                gameOver();
            }

            tvPlayer1.setText(String.valueOf(player2.size()));
            tvPlayer2.setText(String.valueOf(player1.size()));
        }
    }

    public void gameOver() {
        gameOver = true;
        if (player1.size() > player2.size()) {  //player1 wins
            whoWin.setText("player 1 wins");

        } else {    //player2 wins
            whoWin.setText("player 2 wins");
        }
    }

    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {

        });
    }
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {

        });
    }
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}

