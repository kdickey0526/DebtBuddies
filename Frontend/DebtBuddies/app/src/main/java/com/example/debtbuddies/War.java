package com.example.debtbuddies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
public class War extends AppCompatActivity {
    TextView tvPlayer1, tvPlayer2, whoWin;
    ImageView cardPlayer1, cardPlayer2;
   PlayerStats p;  // will remove
    boolean win;
    int bet = 5;
    boolean gameOver, playerWin;

    ArrayList<Card> player1 = new ArrayList<Card>();
    ArrayList<Card> player2 = new ArrayList<Card>();
    @SuppressLint("MissingInflatedId")
    @Override
    /**
     * creates the deck and each player's cards
     */
    protected void onCreate(Bundle savedInstanceState) {
        p = new PlayerStats();
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
        for (int i = 52; i > 2; i --) { //change back to 26
            temp = r.nextInt(i);
            player1.add(deck.get(temp));
            deck.remove(temp);

        }
        player2 = deck;

        tvPlayer1.setText(String.valueOf(player2.size()));
        tvPlayer2.setText(String.valueOf(player1.size()));
    }

    /**
     * deals the card to each player
     * if a war similates the war
     * @param view
     */
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

    /**
     * displays the winner of the game
     */
    public void gameOver() {
        gameOver = true;
        if (player1.size() > player2.size()) {  //player1 wins
            whoWin.setText("player 1 wins");
            playerWin = true;
            win = true;
        } else {    //player2 wins
            whoWin.setText("player 2 wins");
            playerWin = false;
            win = false;
        }
        p.gameStats(playerWin, bet, 50);
//        Intent intent = new Intent(this, GameOver.class);
//        startActivity(intent);
       // PlayerStats p = new PlayerStats();
    }

    /**
     * leaves the game
     * game must be over
     * @param view
     */
    public void onMenuClicked(View view) {
        if (gameOver == true) {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }
    }

    public Boolean getWin() {
        return win;
    }

}

