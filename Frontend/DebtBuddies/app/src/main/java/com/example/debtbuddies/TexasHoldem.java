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

public class TexasHoldem extends AppCompatActivity implements WebSocketListener {
    private static final String TAG = "TexasHoldem";
    String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/gameserver/IDK";
    String connectedURL;
    Boolean gameStart;
    EditText tv_raise;
    String player2, player3, player4;
    int players;
    int pot, ante;
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

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }
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


    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}