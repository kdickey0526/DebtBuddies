package com.example.debtbuddies;

import static com.example.debtbuddies.MyApplication.currentUser;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Activity for the Texas Hold 'Em game.
 * See "https://en.wikipedia.org/wiki/Texas_hold_%27em" for more information on how the game works.
 */
public class TexasHoldem extends AppCompatActivity implements WebSocketListener {
    private static final String TAG = "TexasHoldem";
    String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/gameserver/IDK";
    String connectedURL;
    Boolean gameStart;
    EditText tv_raise;
    String player2, player3, player4;
    int players;

    int pot, ante;

    /**
     * Instantiates the websocket connection with the backend for the game.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texas_hold);


        // connect to websocket
        try {
            connectedURL = baseURL + MyApplication.currentUser.getString("name");
            WebSocketManager.getInstance().connectWebSocket(connectedURL);
            WebSocketManager.getInstance().setWebSocketListener(TexasHoldem.this);
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "joinQueue" + "\"}");

        tv_raise = findViewById(R.id.tv_raise);

        gameStart = false;
        players = 0;
        pot = 0;
        ante = 0;
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
        String val = tv_raise.toString();

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

            if (temp[0].charAt(8) == 's') { // stage logic

            } else if (temp[0].charAt(8) == 't') {   //get player info

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
                pot = Integer.parseInt(s_pot);
                ante = Integer.parseInt(s_ante);

                if (hold.equals(player2)) {
                    if (move.equals("fold")) {

                    } else if (move.equals("call")) {

                    } else if (move.equals("raise")) {

                    }
                } else if (hold.equals(player3)) {

                } else if (hold.equals(player4)) {

                }
            }

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

    }

    /**
     * Actions to complete on web socket error. WIP.
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }
}