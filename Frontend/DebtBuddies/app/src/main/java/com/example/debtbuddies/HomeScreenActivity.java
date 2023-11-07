package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView usernameField;
    private TextView coinsField;
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/";
    private static final String TAG = "HomeScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        // instantiate views
        usernameField = (TextView) findViewById(R.id.usernameText);
        coinsField = (TextView) findViewById(R.id.coinsText);

        // set username & coins based on current user
        // this code will only work when server is running and logged into an actual user
        if (!MyApplication.loggedInAsGuest) {
            try {
                // re-fetch user information to update fields
                SERVER_URL += MyApplication.currentUser.getString("name").toString();
                makeJsonObjReq(); // should update currentUser
                usernameField.setText(MyApplication.currentUser.getString("name"));
                coinsField.setText(MyApplication.currentUser.getInt("coins") + " coins");
            } catch (JSONException e) {
                Log.e(TAG, "failed getting/setting username and coins in text views");
                e.printStackTrace();
            }
        } else {
            usernameField.setText("Guest");
            coinsField.setText("No coins");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!MyApplication.loggedInAsGuest) {
            try {
                // re-fetch user information to update fields
                SERVER_URL += MyApplication.currentUser.getString("name").toString();
                makeJsonObjReq(); // should update currentUser
                usernameField.setText(MyApplication.currentUser.getString("name"));
                coinsField.setText(MyApplication.currentUser.getInt("coins") + " coins");
            } catch (JSONException e) {
                Log.e(TAG, "failed setting username and coins in text views");
                e.printStackTrace();
            }
        } else {
            usernameField.setText("Guest");
            coinsField.setText("No coins");
        }
    }

    public void whacAMoleOnClickListener(View v) {
        Log.d(TAG, "whacAMoleOnClickListener: clicked");
        startActivity(new Intent(this, WhacAMoleActivity.class));
    }

    public void blackjackOnClickListener(View v) {
        Log.d(TAG, "blackjackOnClickListener: clicked");
        startActivity(new Intent(this, BlackJack.class));
    }

    public void warOnClickListener(View v) {
        Log.d(TAG, "warOnClickListener: clicked");
        startActivity(new Intent(this, WarMultiplayer.class));
    }

    public void friendsListOnClickListener(View v) {
        Log.d(TAG, "friendsListOnClickListener: clicked");
        startActivity(new Intent(this, FriendsListActivity.class));
    }

    public void settingsOnClickListener(View v) {
        Log.d(TAG, "settingsOnClickListener: clicked");

    }

    public void chatOnClickListener(View v) {
        Log.d(TAG, "chatOnClickListener: clicked, opening chat box");
        startActivity(new Intent(this, GlobalChatActivity.class));
    }

    public void leaderboardOnClickListener(View v) {
        Log.d(TAG, "leaderboardOnClickListener: clicked");

    }

    public void profileOnClickListener(View v) {
        Log.d(TAG, "profileOnClickListener: clicked");
        startActivity(new Intent(this, Menu.class));
    }
    /**
     * Making json object request
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                MyApplication.currentUser = response;
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