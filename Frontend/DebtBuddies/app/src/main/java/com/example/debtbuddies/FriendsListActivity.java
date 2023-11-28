package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Screen which displays the current user's friends list. Displays their
 * name and whether they are online.
 */
public class FriendsListActivity extends AppCompatActivity {

    private TextView listOfFriends;

    /**
     * Runs when the screen is navigated to. Initializes UI elements
     * and fetches the current user's friends.
     * @param savedInstanceState the instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // instantiate views
        listOfFriends = findViewById(R.id.tv_friends);

        // request user's friends list from backend here

        // for each loop for each friend in the list/json object
            // display each user adding onto the text view, similar to how global chat was implemented
            // also display the user's online status
    }
}