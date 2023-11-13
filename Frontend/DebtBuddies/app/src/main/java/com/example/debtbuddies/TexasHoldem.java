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

/**
 * Activity for the Texas Hold 'Em game.
 * See "https://en.wikipedia.org/wiki/Texas_hold_%27em" for more information on how the game works.
 */
public class TexasHoldem extends AppCompatActivity implements WebSocketListener {
    private static final String TAG = "TexasHoldem";
    String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/gameserver/IDK";
    String connectedURL;
    Boolean gameStart;
    int players;

    /**
     * Instantiates the websocket connection with the backend for the game.
     * @param savedInstanceState
     */
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