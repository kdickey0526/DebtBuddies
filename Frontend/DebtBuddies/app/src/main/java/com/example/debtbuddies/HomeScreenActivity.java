package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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

/**
 * The home screen for the app. Responsible for navigation to leaderboard, settings, profile, games, etc.
 * Also displays basic information about the user (name and coins)
 */
public class HomeScreenActivity extends AppCompatActivity {

    private TextView usernameField;
    private TextView coinsField;

    private static final String TAG = "HomeScreenActivity";
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/";

    /**
     * Sets up the UI elements and fetches user information.
     * @param savedInstanceState the current instance of the app
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        // instantiate views
        usernameField = (TextView) findViewById(R.id.usernameText);
        coinsField = (TextView) findViewById(R.id.coinsText);

        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }

        // set username & coins based on current user
        // this code will only work when server is running and logged into an actual user
        if (!MyApplication.loggedInAsGuest) {
            try {
                try {
                    SERVER_URL += MyApplication.currentUserName;
                    makeJsonObjReq();   // refresh the currentUser object
                } catch (Exception e) {
                    Log.e(TAG, "failed fetching the user information");
                    e.printStackTrace();
                }

                Log.d(TAG, MyApplication.currentUser.toString());
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

        updateUserSettings();
    }

    @Override
    protected void onResume() {
        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }
        super.onResume();
    }

    /**
     * Listener to display the Whac-A-Mole game.
     * @param v the button/image for whac-a-mole
     */
    public void whacAMoleOnClickListener(View v) {
        Log.d(TAG, "whacAMoleOnClickListener: clicked");
        startActivity(new Intent(this, WhacAMoleActivity.class));
    }

    /**
     * Listener to display the Blackjack game.
     * @param v the button/image for blackjack
     */
    public void blackjackOnClickListener(View v) {
        Log.d(TAG, "blackjackOnClickListener: clicked");
        startActivity(new Intent(this, BlackJack.class));
    }

    /**
     * Listener to display the War game.
     * @param v the button/image for war
     */
    public void warOnClickListener(View v) {
        Log.d(TAG, "warOnClickListener: clicked");
        startActivity(new Intent(this, WarMultiplayer.class));
    }

    public void texasOnClickListener(View v) {
        Log.d(TAG, "texasOnClickListener: clicked");
        startActivity(new Intent(this, TexasHoldem.class));
    }

    /**
     * Listener for displaying the friends list
     * @param v the button/image for friends list
     */
    public void friendsListOnClickListener(View v) {
        Log.d(TAG, "friendsListOnClickListener: clicked");
        startActivity(new Intent(this, FriendsListActivity.class));
    }

    /**
     * Listener for displaying the settings
     * @param v the button/image for settings
     */
    public void settingsOnClickListener(View v) {
        Log.d(TAG, "settingsOnClickListener: clicked");
        startActivity(new Intent(this, SettingsActivity.class));
    }

    /**
     * The listener for displaying the global chat
     * @param v the button/image for global chat
     */
    public void chatOnClickListener(View v) {
        Log.d(TAG, "chatOnClickListener: clicked, opening chat box");
        startActivity(new Intent(this, GlobalChatActivity.class));
    }

    /**
     * The listener for displaying the leaderboard
     * @param v the button/image for the leaderboard
     */
    public void leaderboardOnClickListener(View v) {
        Log.d(TAG, "leaderboardOnClickListener: clicked");
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    /**
     * The listener for displaying the current user's profile information
     * @param v the button/image for the profile
     */
    public void profileOnClickListener(View v) {
        Log.d(TAG, "profileOnClickListener: clicked");
        startActivity(new Intent(this, Menu.class));
    }

    /**
     * Makes a json object request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                MyApplication.currentUser = response; // store json object
            }
        }, new Response.ErrorListener() {
            /**
             * Displays the error to Logcat.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    /**
     * Helper method. Sets the user's current settings.
     */
    public void updateUserSettings() {
        if (!MyApplication.loggedInAsGuest) {
            // get user's settings stored in database and update accordingly in MyApplication
        }
    }
}