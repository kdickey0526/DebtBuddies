package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class CreateParty extends AppCompatActivity implements WebSocketListener {
    Button b_back;
    String member1, member2, member3;

    String SERVER_URL= "";
    EditText tv_member1, tv_member2, tv_member3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        b_back = findViewById(R.id.b_submit);

        tv_member1 = findViewById(R.id.member1);
        tv_member2 = findViewById(R.id.member2);
        tv_member3 = findViewById(R.id.member3);
        WebSocketManager.getInstance().connectWebSocket(SERVER_URL);
        WebSocketManager.getInstance().setWebSocketListener(CreateParty.this);
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, Party.class);
        startActivity(intent);
    }

    public void onSendClicked(View v) {
        member1 = tv_member1.getText().toString();
        member2 = tv_member2.getText().toString();
        member3 = tv_member3.getText().toString();

        if (!(member1.equals("Enter Party Member"))) {
            Toast.makeText(this,"Invited " + member1 ,Toast.LENGTH_SHORT).show();
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(member1);
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        }
        if (!(member2.equals("Enter Party Member"))) {
            Toast.makeText(this,"Invited " + member2 ,Toast.LENGTH_SHORT).show();
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(member2);
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        }
        if (!(member2.equals("Enter Party Member"))) {
            Toast.makeText(this,"Invited " + member3 ,Toast.LENGTH_SHORT).show();
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(member3);
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        }
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
