package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView usernameField;
    private TextView coinsField;
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
//        try {
//            usernameField.setText(MyApplication.currentUser.getString("userName"));
//            coinsField.setText(MyApplication.currentUser.getString("coins"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e(TAG, "failed setting username and coins");
//        }
    }

    public void whacAMoleOnClickListener(View v) {
        Log.d(TAG, "whacAMoleOnClickListener: clicked");
        
    }

    public void blackjackOnClickListener(View v) {
        Log.d(TAG, "blackjackOnClickListener: clicked");
        startActivity(new Intent(this, BlackJack.class));
    }

    public void friendsListOnClickListener(View v) {
        Log.d(TAG, "friendsListOnClickListener: clicked");

    }

    public void settingsOnClickListener(View v) {
        Log.d(TAG, "settingsOnClickListener: clicked");

    }

    public void leaderboardOnClickListener(View v) {
        Log.d(TAG, "leaderboardOnClickListener: clicked");

    }
}