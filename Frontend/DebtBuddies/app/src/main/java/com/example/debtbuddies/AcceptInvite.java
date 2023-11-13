package com.example.debtbuddies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 
 */
public class AcceptInvite extends AppCompatActivity implements WebSocketListener {

    TextView tv_username, status;
    Button b_accept, b_decline, b_back;
    String SERVER_URL = " ";
    ImageView icon;
    String result;
    @Override
    /**
     * On creation initlizes data
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_invite);

        tv_username = findViewById(R.id.tv_username);
        b_accept = findViewById(R.id.b_accept);
        b_decline = findViewById(R.id.b_decline);
        b_back = findViewById(R.id.b_submit);
        icon = findViewById(R.id.icon);
        status = findViewById(R.id.textView2);
        WebSocketManager.getInstance().connectWebSocket(SERVER_URL);
        WebSocketManager.getInstance().setWebSocketListener(AcceptInvite.this);
    }

    public void onAcceptClicked(View view) {

        result = "accept";
    }
    public void onDeclineClicked(View view) {
        result = "decline";

    }
    public void onBackClicked(View view) {
        Intent intent = new Intent(this, Party.class);
        startActivity(intent);
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
            status.setText(message);

        });
    }
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        runOnUiThread(() -> {
        });
    }
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }


}
