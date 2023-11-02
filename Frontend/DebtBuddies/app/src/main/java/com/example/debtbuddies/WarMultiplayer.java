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
import android.util.Log;
public class WarMultiplayer extends AppCompatActivity implements WebSocketListener {
    TextView tvPlayer1, tvPlayer2, whoWin;
    ImageView cardPlayer1, cardPlayer2;

    boolean gameOver;

    int cardVal;
    String cards;

    String serverUrl = "";  //need the correct URL

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

        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(WarMultiplayer.this);


    }

    public void onDealClicked (View view) {
        if (gameOver != true) {
            int player1val = 0;
            int player2val = 0;
            int image;


            try {
                // send message
                WebSocketManager.getInstance().sendMessage("Deal");
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }



            image = getResources().getIdentifier(Integer.toString(player1val), "drawable", getPackageName());
            cardPlayer1.setImageResource(image);


            image = getResources().getIdentifier(Integer.toString(player2val), "drawable", getPackageName());
            cardPlayer2.setImageResource(image);

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
        runOnUiThread(() -> {   // data from server
            cards = "";
        });
    }
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

        runOnUiThread(() -> {

        });
    }
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}

