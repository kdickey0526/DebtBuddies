package com.example.debtbuddies;

import static com.example.debtbuddies.MyApplication.currentUser;
import androidx.appcompat.app.AppCompatActivity;
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
    int players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texas_hold);
        try {
            connectedURL = baseURL + MyApplication.currentUser.getString("name");
            WebSocketManager.getInstance().connectWebSocket(connectedURL);
            WebSocketManager.getInstance().setWebSocketListener(TexasHoldem.this);
            Log.d(TAG, "onKey: message successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSocketManager.getInstance().sendMessage("{\"action\":\"" + "joinQueue" + "\"}");

        gameStart = false;
        players = 0;
    }


    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }
    @Override
    public void onWebSocketMessage(String message) {    //THIS WILL NEED CHANGES
        runOnUiThread(() -> {   // data from server
            if (gameStart == false) {
                players ++;
                if (players == 1) {

                } else if (players == 2) {

                } else {

                    gameStart = true;
                }

            } else {

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