package com.example.debtbuddies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.Random;
import android.util.Log;
public class WarMultiplayer extends AppCompatActivity implements WebSocketListener {

    private static final String TAG = "WarMultiplayer";
    TextView tvPlayer1, tvPlayer2, whoWin,tv_temp;
    ImageView cardPlayer1, cardPlayer2;
    boolean gameOver;
    private String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/gameserver/war/";
    String connectedURL;
    int cardVal;
    String cards;
    boolean gameStart;
    String serverUrl = "";  //need the correct URL
    private String BASE_URL = "ws://10.0.2.2:8080/chat/";
    ArrayList<Card> player1 = new ArrayList<Card>();
    ArrayList<Card> player2 = new ArrayList<Card>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // connect to websocket
        try {
            connectedURL = baseURL + MyApplication.currentUser.getString("name");
            WebSocketManager.getInstance().connectWebSocket(connectedURL);
            WebSocketManager.getInstance().setWebSocketListener(WarMultiplayer.this);
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String suit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);

        tvPlayer1 = findViewById(R.id.tv_player1);
        tvPlayer2 = findViewById(R.id.tv_player2);

        whoWin = findViewById(R.id.tv_text);

        cardPlayer1 = findViewById(R.id.id_player1);
        cardPlayer2 = findViewById(R.id.id_player2);

        tv_temp = findViewById(R.id.tv_temp);

        WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "joinQueue" + "\"}");

        gameOver = false;
    }

    public void onDealClicked (View view) {
        if (gameOver != true) {
            try {
                WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "deal" + "\"}");
                Log.d(TAG, "onKey: message successful");
            } catch (Exception e) {
                // shouldn't throw any exceptions, but just in case
                Log.d(TAG, "onKey: Exception when sending message");
                e.printStackTrace();
            }
        }
    }
    public void onMenuClicked(View view) {
        if (gameOver == true) {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }
    }
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "War: websocket opened");
    }
    @Override
    public void onWebSocketMessage(String message) {    //THIS WILL NEED CHANGES
        runOnUiThread(() -> {   // data from server
            if (gameStart == true) {
                cards = message;
                if (message.charAt(0) != '{') {
                    String hold = "";

                    String[] temp = cards.split(" ");
                    tv_temp.setText(message);

                    for (int i = 1; i < temp[0].length(); i ++) {
                        hold += temp[0].charAt(i);
                    }
                    int image = getResources().getIdentifier(hold, "drawable", getPackageName());
                    cardPlayer1.setImageResource(image);

                    hold = "";
                    for (int i = 0; i < temp[1].length() - 1; i ++) {
                        hold += temp[1].charAt(i);
                    }
                    image = getResources().getIdentifier(hold, "drawable", getPackageName());
                    cardPlayer2.setImageResource(image);

                } else {

                }
            } else {
                gameStart = true;
            }
            
        });
    }
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

        runOnUiThread(() -> {

        });
    }

    @Override
    public void onWebSocketError(Exception ex) {}
}

