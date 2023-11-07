package com.example.debtbuddies;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

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


public class Menu extends AppCompatActivity  {

    private static final String TAG = "Menu";
    Button b_back, b_menu,b_party;
    ImageView icon;
    String serverUrl = "http://coms-309-048.class.las.iastate.edu:8080/person/";
    TextView tv_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv_username = findViewById(R.id.tv_username);

        b_back = findViewById(R.id.b_submit);
        b_party = findViewById(R.id.b_party);
        b_menu = findViewById(R.id.b_main_menu);


        icon = findViewById(R.id.icon);

        if (!MyApplication.loggedInAsGuest) {
            try {
                serverUrl += MyApplication.currentUser.getString("name");
            } catch (Exception e) {
                Log.d(TAG, "Not logged in as guest, failed to get name field from currentUser");
            }
        }
        makeJsonObjReq();

    }

    public void onMenuClicked (View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
    public void onIconsClicked (View view) {
        Intent intent = new Intent(this, ProfileIcons.class);
        startActivity(intent);
    }
    public void onPartyClicked (View view) {
        Intent intent = new Intent(this, Party.class);
        startActivity(intent);
    }
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, serverUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    String temp = response.getString("Profile");
                    int image = getResources().getIdentifier(temp, "drawable", getPackageName());
                    icon.setImageResource(image);

                    MyApplication.currentUser = response;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

}
