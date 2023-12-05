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
 * Class representing the Global Chat. Connects to the global chat over websockets based on the current user.
 */
public class GlobalChatActivity extends AppCompatActivity implements WebSocketListener {

    private static final String TAG = "GlobalChatActivity";
    private EditText userMsg;
    private TextView currentChat;
    private ScrollView scrollView;
    private String userText = "";
    private String baseURL = "ws://coms-309-048.class.las.iastate.edu:8080/chat/"; //"ws://10.0.2.2:8080/chat/";
    private String connectedURL;

    /**
     * Runs when starting the global chat. Initializes UI elements, listeners, and connects the websockets.
     * @param savedInstanceState the current instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);

        // connect to websocket
        try {
            connectedURL = baseURL + MyApplication.currentUser.getString("name");
            WebSocketManager.getInstance().connectWebSocket(connectedURL);
            WebSocketManager.getInstance().setWebSocketListener(GlobalChatActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // instantiate user input text (& chat) and allow messages to be sent by pressing enter
        currentChat = findViewById(R.id.tv_globalChat);
        scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        userMsg = findViewById(R.id.et_userMsg);
        userMsg.setFocusableInTouchMode(true);
        userMsg.setFocusable(true);
        userMsg.requestFocus();
        userMsg.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Listener for the chat message box that takes user input. Listens for the enter/return key to be pressed,
             * and then sends the message typed into the box.
             * @param view the EditText message textbox
             * @param i the keycode/key pressed
             * @param keyEvent the state of the key (pressed/down)
             * @return true if enter is pressed false otherwise
             */
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
                            Log.d(TAG, "onKey: message successful");
                        } catch (Exception e) {
                            // shouldn't throw any exceptions, but just in case
                            Log.d(TAG, "onKey: Exception when sending message");
                            e.printStackTrace();
                        }
                        userMsg.setText("");
                        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                    }
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * Initializes the view where the global chat appears.
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "Global Chat: websocket opened");
        currentChat.setText(""); // get rid of the "The global chat's messages will appear here" msg
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        scrollView.setFocusable(ScrollView.NOT_FOCUSABLE);
    }

    /**
     * Updates the global chat box and scrolls down to most recent messages.
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        Log.d(TAG, "onWebSocketMessage: sent message");
        runOnUiThread(() -> {
            // store the current chat into temp variable s
            String s = currentChat.getText().toString();
            // update the chat to display s and the new message
            if (!currentChat.getText().toString().equals("")) {
                currentChat.setText(s + "\n" + message);
            } else {
                currentChat.setText(message);
            }
            scrollView.setFocusable(ScrollView.NOT_FOCUSABLE);
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            scrollView.setFocusable(ScrollView.NOT_FOCUSABLE);
        });
    }

    /**
     * Updates the chat box to reflect the fact that the web socket has closed.
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            // store current chat into temp variable s
            String s = currentChat.getText().toString();
            currentChat.setText(s + "\n--- Chat closed by " + closedBy + " due to " + reason + "---");
        });
    }

    /**
     * Outputs information to Logcat about the websocket error.
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {
        Log.e(TAG, "Global Chat: websocket error occured");
        ex.printStackTrace();
    }

    /**
     * Disconnects the websocket and runs regular onPause functions.
     */
    @Override
    public void onPause() {
        WebSocketManager.getInstance().disconnectWebSocket();
        super.onPause();
    }

}