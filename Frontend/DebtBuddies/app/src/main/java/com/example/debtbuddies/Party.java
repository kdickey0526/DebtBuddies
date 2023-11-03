package com.example.debtbuddies;
import static android.text.TextUtils.replace;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

public class Party extends AppCompatActivity implements WebSocketListener {

    String username = "Brock";
    String icon = "icon4";
    ImageView playerIcon, member1, member2,member3,member4;
    TextView playerUsername;
    Button joinParty, createParty;

    String serverUrl = "";

    String webIcon;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        playerUsername = findViewById(R.id.textView);
        playerIcon = findViewById(R.id.icon);
        joinParty = findViewById(R.id.b_join);
        createParty = findViewById(R.id.b_create);

        member1 = findViewById(R.id.icon);
        member2 = findViewById(R.id.icon2);
        member3 = findViewById(R.id.icon3);
        member4 = findViewById(R.id.icon4);


        int image = getResources().getIdentifier(icon, "drawable", getPackageName());
        playerIcon.setImageResource(image);

        playerUsername.setText(username);
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(Party.this);

        try {
            // send message
            WebSocketManager.getInstance().sendMessage("member Join ");
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }
    }

    public void onMenuClicked(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void onCreate(View v){
        Intent intent = new Intent(this, CreateParty.class);
        startActivity(intent);
    }
    public void onJoin(View v){
        Intent intent = new Intent(this, AcceptInvite.class);
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
            webIcon = message;
            int image = getResources().getIdentifier(message, "drawable", getPackageName());
            i++;
            if (i == 1) {
                member1.setImageResource(image);
            }
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


