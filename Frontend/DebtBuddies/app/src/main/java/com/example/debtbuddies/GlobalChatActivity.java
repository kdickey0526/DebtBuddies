package com.example.debtbuddies;

import static com.example.debtbuddies.MyApplication.currentUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;

public class GlobalChatActivity extends AppCompatActivity implements WebSocketListener {

    private static final String TAG = "GlobalChatActivity";
    private EditText userMsg;
    private TextView currentChat;
    private String userText = "";
    private String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/chat/";
    private String connectedURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);

        // connect to websocket
        try {
            connectedURL = baseURL + MyApplication.currentUser.getString("userName");
            WebSocketManager.getInstance().connectWebSocket(connectedURL);
            WebSocketManager.getInstance().setWebSocketListener(GlobalChatActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // instantiate user input text (& chat) and allow messages to be sent by pressing enter
        currentChat = findViewById(R.id.tv_globalChat);
        userMsg = findViewById(R.id.et_userMsg);
        userMsg.setFocusableInTouchMode(true);
        userMsg.requestFocus();
        userMsg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // if user presses enter, send message
                if ((keyEvent.getAction()) == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                    userText = userMsg.getText().toString();
                    userText = userText.trim();
                    // make sure message being sent isn't just white space
                    if (!userText.equals("") || !userText.isEmpty()) {
                        // send the message
                        try {
                            WebSocketManager.getInstance().sendMessage(userText);
                        } catch (Exception e) {
                            // shouldn't throw any exceptions, but just in case
                            Log.d(TAG, "onKey: Exception when sending message");
                            e.printStackTrace();
                        }
                        userMsg.setText("");
                    }
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onWebSocketOpen: websocket opened");
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            // store the current chat into temp variable s
            String s = currentChat.getText().toString();
            // update the chat to display s and the new message
            currentChat.setText(s + "\n" + message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            // store current chat into temp variable s
            String s = currentChat.getText().toString();
            currentChat.setText(s + "\n--- Chat closed by " + closedBy + " due to " + reason);
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {
        Log.e(TAG, "onWebSocketError: error occured");
        ex.printStackTrace();
    }

}