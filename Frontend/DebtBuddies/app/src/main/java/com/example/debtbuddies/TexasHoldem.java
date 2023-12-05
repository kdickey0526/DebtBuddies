package com.example.debtbuddies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.Random;
import android.util.Log;

/**
 * Activity for the Texas Hold 'Em game.
 * See "https://en.wikipedia.org/wiki/Texas_hold_%27em" for more information on how the game works.
 */
public class TexasHoldem extends AppCompatActivity implements WebSocketListener {
    private static final String TAG = "TexasHoldem";
    String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/gameserver/texasholdem/";
    String connectedURL;
    Boolean gameStart;
    EditText tv_raise;
    String player2, player3, player4;
    int players;

    int pot, ante, count;
    int counter;

    ImageView cardPlayer1, cardPlayer2, community1, community2, community3, community4, community5;


    String icon, playerName;
    int bal;
    ImageView playerIcon;
    TextView username, playerBal, tv_pot, tv_ante;
    /**
     * Instantiates the websocket connection with the backend for the game.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//         connect to websocket
        if (!MyApplication.loggedInAsGuest) {
            try {
                connectedURL = baseURL + MyApplication.currentUser.getString("name");
                playerName = MyApplication.currentUser.getString("name");
                icon = MyApplication.currentUser.getString("Profile");
                bal = MyApplication.currentUser.getInt("coins");
                WebSocketManager.getInstance().connectWebSocket(connectedURL);
                WebSocketManager.getInstance().setWebSocketListener(TexasHoldem.this);
                Log.d(TAG, "onKey: message successful");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texas_hold);

        WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "joinQueue" + "\"}");

        tv_raise = findViewById(R.id.tv_raise);
        cardPlayer1 = findViewById(R.id.id_player1);
        cardPlayer2 = findViewById(R.id.id_player2);

        community1 = findViewById(R.id.id_community1);
        community2 = findViewById(R.id.id_community2);
        community3 = findViewById(R.id.id_community3);
        community4 = findViewById(R.id.id_community4);
        community5 = findViewById(R.id.id_community5);

        playerIcon = findViewById(R.id.icon);
        username = findViewById(R.id.tv_player1_username);
        playerBal = findViewById(R.id.tv_player1_bal);


        int image = getResources().getIdentifier(icon, "drawable", getPackageName());
        playerIcon.setImageResource(image);

        username.setText(playerName);
        playerBal.setText(bal);

        tv_pot = findViewById(R.id.tv_pot);
        tv_ante = findViewById(R.id.tv_ante);

        gameStart = false;
        players = 0;
        pot = 0;
        ante = 0;
        count = 0;
        counter = 0;
    }
    public void onFoldClicked(View view) {
        try {
            WebSocketManager.getInstance().sendMessage(
                    "{\"action\":\"" + "fold" + "\"" + "}");
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            // shouldn't throw any exceptions, but just in case
            Log.d(TAG, "onKey: Exception when sending message");
            e.printStackTrace();
        }
    }
    public void onCallClicked(View view) {
        try {
            WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "call" + "\"}");
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            // shouldn't throw any exceptions, but just in case
            Log.d(TAG, "onKey: Exception when sending message");
            e.printStackTrace();
        }
    }
    public void onRaiseClicked(View view) {
        String val = tv_raise.getText().toString();

        try {
            WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "raise" + "\"" +
                                                            ",\"value\":" +
                                                             ":\"" + val + "\"" + "}");
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            // shouldn't throw any exceptions, but just in case
            Log.d(TAG, "onKey: Exception when sending message");
            e.printStackTrace();
        }
    }

    /**
     * Required function for implementing websockets. Doesn't do much.
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * What to do on the front-end for each response from backend. Updates UI to represent
     * state of game. WIP.
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {    //THIS WILL NEED CHANGES
        runOnUiThread(() -> {   // data from server

            String[] temp = message.split(",");
            String hold = "";
            String move = "";
            String s_ante = "";
            String s_pot = "";

            if (temp[0].charAt(9) == 's' && temp[0].charAt(12) == 'r') { // player hand
                String card = "";
                char suit = temp[1].charAt(31);
                String rank = "";

                for (int j = 9; j < temp[2].length() - 1; j++) {
                    rank += temp[2].charAt(j);
                }

                if (suit == 'S') { //spades
                    card += "spade";
                } else if (suit == 'C') { //club
                    card += "club";
                } else if (suit == 'C') { //diamond
                    card += "heart";
                } else if (suit == 'C') { //heart
                    card += "heart";
                }
                card += rank;

                int image = getResources().getIdentifier(card, "drawable", getPackageName());
                cardPlayer1.setImageResource(image);

                card = "";
                suit = temp[3].charAt(12);
                rank = "";

                for (int j = 9; j < temp[4].length() - 1; j++) {
                    rank += temp[4].charAt(j);
                }

                if (suit == 'S') { //spades
                    card += "spade";
                } else if (suit == 'C') { //club
                    card += "club";
                } else if (suit == 'C') { //diamond
                    card += "heart";
                } else if (suit == 'C') { //heart
                    card += "heart";
                }
                card += rank;

                image = getResources().getIdentifier(card, "drawable", getPackageName());
                cardPlayer2.setImageResource(image);


            }  else if (temp[0].charAt(9) == 's') {  //stage info
                count ++;
                counter++;
                int image;
                String card = "";
                String rank = "";
                char suit;

                if (counter == 1) {
                    suit = temp[1].charAt(30); //was 25
                    rank = "";
                    for (int j = 9; j < temp[2].length() - 1; j++) {
                        rank += temp[2].charAt(j);
                    }

                    if (suit == 'S') { //spades
                        card += "spade";
                    } else if (suit == 'C') { //club
                        card += "club";
                    } else if (suit == 'C') { //diamond
                        card += "heart";
                    } else if (suit == 'C') { //heart
                        card += "heart";
                    }
                    card += rank;

                    image = getResources().getIdentifier(card, "drawable", getPackageName());
                    community1.setImageResource(image);


                    card = "";
                    suit = temp[3].charAt(12);
                    rank = "";

                    for (int j = 9; j < temp[4].length() - 1; j++) {
                        rank += temp[4].charAt(j);
                    }

                    if (suit == 'S') { //spades
                        card += "spade";
                    } else if (suit == 'C') { //club
                        card += "club";
                    } else if (suit == 'C') { //diamond
                        card += "heart";
                    } else if (suit == 'C') { //heart
                        card += "heart";
                    }
                    card += rank;

                    image = getResources().getIdentifier(card, "drawable", getPackageName());
                    community2.setImageResource(image);

                    card = "";
                    suit = temp[5].charAt(12);
                    rank = "";

                    for (int j = 9; j < temp[6].length() - 5; j++) {
                        rank += temp[6].charAt(j);
                    }

                    if (suit == 'S') { //spades
                        card += "spade";
                    } else if (suit == 'C') { //club
                        card += "club";
                    } else if (suit == 'C') { //diamond
                        card += "heart";
                    } else if (suit == 'C') { //heart
                        card += "heart";
                    }
                    card += rank;

                    image = getResources().getIdentifier(card, "drawable", getPackageName());
                    community3.setImageResource(image);
                }else
                if (counter == 2) {
                    card = "";
                    suit = temp[7].charAt(12);
                    rank = "";

                    for (int j = 9; j < temp[8].length() - 5; j++) {
                        rank += temp[8].charAt(j);
                    }

                    if (suit == 'S') { //spades
                        card += "spade";
                    } else if (suit == 'C') { //club
                        card += "club";
                    } else if (suit == 'C') { //diamond
                        card += "heart";
                    } else if (suit == 'C') { //heart
                        card += "heart";
                    }
                    card += rank;

                    image = getResources().getIdentifier(card, "drawable", getPackageName());
                    community4.setImageResource(image);
                } else if (counter == 3) {
                    card = "";
                    suit = temp[9].charAt(12);
                    rank = "";

                    for (int j = 9; j < temp[10].length() - 5; j++) {
                        rank += temp[10].charAt(j);
                    }

                    if (suit == 'S') { //spades
                        card += "spade";
                    } else if (suit == 'C') { //club
                        card += "club";
                    } else if (suit == 'C') { //diamond
                        card += "heart";
                    } else if (suit == 'C') { //heart
                        card += "heart";
                    }
                    card += rank;

                    image = getResources().getIdentifier(card, "drawable", getPackageName());
                    community5.setImageResource(image);
                }
            }
            else if (temp[0].charAt(8) == 't') {   //get player info

            } else if (temp[0].charAt(8) == 'd'){    // move logic

                /**
                 * {"type":"turnInfo","data":
                 * "{\"move\":\"call\",
                 * \"next\":\"Kyle\",
                 * \"pot\":30,
                 * \"ante\":10}"}
                 */

                for (int i = 7; i < temp[1].length(); i ++) { // get what player the turn belongs to
                    hold += temp[1].charAt(i);
                }
                for (int i = 9; i < temp[2].length(); i ++) { // get the move type
                    move += temp[2].charAt(i);
                }
                for (int i = 6; i < temp[3].length(); i ++) { // get the pot
                    s_pot += temp[2].charAt(i);
                }
                for (int i = 7; i < temp[4].length(); i ++) { // get the ante
                    s_ante += temp[2].charAt(i);
                }
                //pot = Integer.parseInt(s_pot);
               // ante = Integer.parseInt(s_ante);
                tv_pot.setText("Pot: " + s_pot);
                tv_ante.setText("Ante: " + s_ante);

                if (hold.equals(player2)) {
                    if (move.equals("fold")) {

                    } else if (move.equals("call")) {

                    } else if (move.equals("raise")) {

                    }
                } else if (hold.equals(player3)) {

                } else if (hold.equals(player4)) {

                }
            }
//
        });
    }


    /**
     * Actions to complete on web socket closing. WIP.
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {

        });
    }

    /**
     * Actions to complete on web socket error. WIP.
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }
}