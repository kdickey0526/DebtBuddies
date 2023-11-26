package com.example.debtbuddies;

import static java.lang.Math.floor;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

/**
 * Activity for the Whac-A-Mole game. The user is allowed to miss three times, the every 5 moles the game gets harder
 * (spawnrate of moles increases and time to click decreases) by 15% (linearly). Requires 5 coins to play. Payout is
 * defined by the level that the user reaches, wherein every two levels earns the user a coin, rounded down if you lose
 * on an odd-numbered level.
 */
public class WhacAMoleActivity extends AppCompatActivity {

    private static final String TAG = "WhacAMoleActivity";
    private Button startBtn;
    private Handler handler;
//    private MyViewModel model;
    private Runnable gameRunnable;
//    private Runnable timeRunnable;
//    private String runningTime = "";
    private TextView level_ui;
    private TextView score;
    private TextView highscore_ui;
    private TextView curMissed;
    private String key_highscore = "key";
    private Mole[] moles;
    private Mole currentMole;
//    private SharedPreferences pref;
//    private SharedPreferences.Editor editor;
    private MediaPlayer mediaPlayer;
    private int highscore;
//    private long hours;
//    private long minutes;
//    private long seconds;
    private long startTime;
    private int missed = 0;
    private static final int MAX_MISSED = 3;
    private int level = 1;
    private int curScore = 0;
    private final int DEFAULT_DURATION = 2000;
    private boolean gameStarted = false;
    private double targetDuration = DEFAULT_DURATION;
    private boolean leaving;
    private boolean depositingCoins;

    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/";

    /**
     * Initializes UI elements, runnables, handlers, sound effects, the moles, etc. Also supports saving
     * data to the device (highscore), but is currently disabled.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whac_amole);

        mediaPlayer = MediaPlayer.create(this, R.raw.pewpew);

        if (!MyApplication.loggedInAsGuest) {
            try {
                SERVER_URL += MyApplication.currentUser.getString("name");
            } catch (Exception e) {
                Log.d(TAG, "Not logged in as guest, failed to get name field from currentUser");
            }
        }

        leaving = false;

        // instantiate views & such
        startBtn = (Button) findViewById(R.id.startBtn);
        highscore_ui = (TextView) findViewById(R.id.highscore);
        level_ui = (TextView) findViewById(R.id.levelindicator);
        curMissed = (TextView) findViewById(R.id.curMissed);
        score = (TextView) findViewById(R.id.score);
        handler = new Handler(getMainLooper());
//        model = new ViewModelProvider(this).get(MyViewModel.class);

        // instantiate runnables (2 of them)
//        timeRunnable = new Runnable() {
//            @Override
//            public void run() {
//                model.currentTime.setValue(SystemClock.uptimeMillis() - startTime);
//                Log.d(TAG, "timeRunnable: updated");
//                handler.postDelayed(this, 100); // update every 100ms
//            }
//        };
        gameRunnable = new Runnable() {
            /**
             * Runnable for the game logic. Determines if the game should continue or stop. Increments level
             * and difficulty when necessary. Called initially at the start and after the target duration has expired.
             */
            @Override
            public void run() {
                if (missed > MAX_MISSED) { // game ends
                    handler.removeCallbacks(gameRunnable);
                    Log.d(TAG, "runnable: game ended");
                    resetGame();
                } else { // game continues
                    Log.d(TAG, "runnable: game continues");

                    // increase difficulty every 5 levels
                    if (level % 5 == 0) {
                        level_ui.setText("Level: " + (level/5 + 1));
                        targetDuration /= 1.15; // 15% harder/faster every level
                    }

                    if (!currentMole.isClicked() && level != 1) { // see if user missed
                        missed++;
                        if (missed > MAX_MISSED) { // make make initial if redundant
                            handler.removeCallbacks(gameRunnable);
                            Log.d(TAG, "runnable: missed too many, ending game");
                            curMissed.setText("Missed: " + missed);
                            resetGame();
                            return;
                        }
                        Log.d(TAG, "runnable: missed target");
                    }

                    currentMole.getMole().setVisibility(View.INVISIBLE);
                    currentMole.setClicked(false);

                    if (level != 1) { // generate new target
                        Random random = new Random();
                        int rand = random.nextInt(12);
                        currentMole = moles[rand];
                    }

                    level++;
                    curMissed.setText("Missed: " + missed);
                    currentMole.getMole().setVisibility(View.VISIBLE);
                    handler.postDelayed(gameRunnable, (long)targetDuration);
                }
            }
        };

        // continue instantiating
        level_ui.setText("Level: " + level);
//        pref = getSharedPreferences(key_highscore, Context.MODE_PRIVATE);
//        editor = pref.edit();
//        highscore = pref.getInt(key_highscore, 0);
        if (!MyApplication.loggedInAsGuest) {
            try {
                highscore = MyApplication.currentUser.getInt("whack");
            } catch (Exception e) {
                Log.e(TAG, "Failed getting current user's whac-a-mole highscore.");
                e.printStackTrace();
            }
        } else {
            highscore = 0;
        }

        highscore_ui.setText("High Score\n" + highscore);
        curMissed.setText("Missed: " + missed);

        // store each mole in moles
        moles = new Mole[12];
        moles[0] = new Mole((ImageView) findViewById(R.id.mole1));
        moles[1] = new Mole((ImageView) findViewById(R.id.mole2));
        moles[2] = new Mole((ImageView) findViewById(R.id.mole3));
        moles[3] = new Mole((ImageView) findViewById(R.id.mole4));
        moles[4] = new Mole((ImageView) findViewById(R.id.mole5));
        moles[5] = new Mole((ImageView) findViewById(R.id.mole6));
        moles[6] = new Mole((ImageView) findViewById(R.id.mole7));
        moles[7] = new Mole((ImageView) findViewById(R.id.mole8));
        moles[8] = new Mole((ImageView) findViewById(R.id.mole9));
        moles[9] = new Mole((ImageView) findViewById(R.id.mole10));
        moles[10] = new Mole((ImageView) findViewById(R.id.mole11));
        moles[11] = new Mole((ImageView) findViewById(R.id.mole12));

        // instantiate observer for time
//        final Observer<Long> timeObserver = new Observer<Long>() {
//            @Override
//            public void onChanged(Long aLong) {
//
//            }
//        };

        // observe the livedata
//        model.getCurrentTime().observe(this, timeObserver);
}

    /**
     * Runs whenever a mole is clicked. Updates the Mole object's variables accordingly.
     * @param view the mole clicked
     */
    public void onMoleClicked(View view) {
        mediaPlayer.start();
        if (gameStarted) {
            // figure out which mole v is
            int i;
            for (i = 0; i < 12; i++) {
                if (moles[i].getMole().equals((ImageView) view)) {
                    view = moles[i].getMole(); // v is now the ImageView mole
                    break;
                }
            }

            if (view.getVisibility() == View.VISIBLE) {
                moles[i].setClicked(true);
                view.setVisibility(View.INVISIBLE);
                score.setText("Score\n" + ++curScore);
                Log.d(TAG, "target clicked, score incremented");
            } else { // shouldn't occur but in case it does (invisible views shouldn't allow listeners to occur)
                     // 10/17/2023: ^^ i think this is wrong, seems to be double counting some close-misses
//                missed++;
//                Log.d(TAG, "missed target");
            }
        }
    }

    /**
     * Listener for the start button. Takes 5 coins from the current user (if not logged in as guest).
     * Initializes the clock, updates UI elements, posts the user's new amount of coins to the backend, and runs
     * startGame().
     * @param view the start button
     */
    public void onStartBtnClicked(View view) {
        if (!gameStarted) {

            // make sure user is logged in and has enough coins to play
            if (!MyApplication.loggedInAsGuest) {
                try {
                    int adjCoinCount = MyApplication.currentUser.getInt("coins");
                    if (adjCoinCount >= 5) { // user needs 5 coins to play
                        adjCoinCount = adjCoinCount - 5;
                        depositingCoins = true;
                        MyApplication.currentUser.put("coins", adjCoinCount);
                        postRequest();
                    } else {
                        Toast.makeText(this, "You need at least 5 coins to play. You have " + adjCoinCount + ".", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Failed to get user's coins");
                }
            }

            startTime = SystemClock.uptimeMillis();

            startBtn.setText(R.string.stop);
            level = 1;
            level_ui.setText("Level: " + level);

            // set moles invisible and unclicked
            for (int i = 0; i < moles.length; i++) {
                moles[i].setClicked(false);
                moles[i].getMole().setVisibility(View.INVISIBLE);
            }

            gameStarted = true;
//            handler.postAtTime(timeRunnable, startTime); // start actively updating time
            startGame();

        } else { // game had started and user clicks give up, restarting game
            resetGame();
        }
    }

    /**
     * Listener for the android built-in back button (shaped like a sideways triangle). Calls resetGame() then
     * regular onBackPressed() functions.
     */
    @Override
    public void onBackPressed() {
        // add code here to update database and SharedPreferences (probably call to resetGame)
        resetGame();
        super.onBackPressed();
    }

    /**
     * Sets up the Moles for the game, as well as other UI elements. Starts the gameRunnable after 1s.
     */
    private void startGame() {
        Log.d(TAG, "startGame called");
        score.setText("Score\n0");

        // set targets invisible & not clicked
        for (int i = 0; i < moles.length; i++) {
            moles[i].setClicked(false);
            moles[i].getMole().setVisibility(View.INVISIBLE);
        }

        Random random = new Random();
        int rand = random.nextInt(12);
        currentMole = moles[rand];

        handler.postDelayed(gameRunnable, 1000); // starts game 1s after clicking start
    }

    /**
     * Removes callbacks on the gameRunnable, resets moles to initial state, updates variables, updates the
     * current user's coins and highscore when necessary.
     */
    private void resetGame() {
        Log.d(TAG, "resetGame called");
        startBtn.setText(R.string.start);

        handler.removeCallbacks(gameRunnable);
//        handler.removeCallbacks(timeRunnable);

        // set targets visible & not clicked
        for (int i = 0; i < moles.length; i++) {
            moles[i].setClicked(false);
            moles[i].getMole().setVisibility(View.VISIBLE);
        }

        gameStarted = false;
        currentMole = null;
        targetDuration = DEFAULT_DURATION;

        if (!leaving) {
            if (!MyApplication.loggedInAsGuest) {
                // give user appropriate # of coins back based on performance
                try {
                    int newCoins = MyApplication.currentUser.getInt("coins");
                    newCoins = newCoins + ((level / 5 + 1) / 2); // ((level/5 + 1) / 2) is the amt of coins user gets
                    // (simply the displayed level / 2)
                    MyApplication.currentUser.put("coins", newCoins);
                    postRequest();
                    if ((level/5 + 1)/2 != 0) {
                        Toast.makeText(this, "Congratulations! You won " + ((level / 5 + 1) / 2) + " coins!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No coins, huh. Better luck next time kiddo.", Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Failed getting coins in resetGame().");
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Congratulations! You would have won " + ((level / 5 + 1) / 2) + " coins!", Toast.LENGTH_SHORT).show();
            }
        }

        level = 1;
        missed = 0;

        // update high score if necessary
        if (curScore > highscore) {
            highscore = curScore;

            // update on backend
            if (!MyApplication.loggedInAsGuest) {
                // UNCOMMENT WHEN THE "WHACK" FIELD IS FIXED
                try {
                    MyApplication.currentUser.put("whack", highscore);
                    postRequest();
                } catch (Exception e) {
                    Log.e(TAG, "Error updating the highscore on the backend");
                    e.printStackTrace();
                }
            }

            // update highscore
            highscore_ui.setText("High Score\n" + highscore);
        }

        curScore = 0;
    }

    /**
     * Updates variables and calls resetGame(), then calls super.onStop().
     */
    @Override
    public void onStop() {
        leaving = true;
        resetGame();
        super.onStop();
    }

    /**
     * Posts updates to the backend (coin count and highscore).
     */
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject(MyApplication.currentUser.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (depositingCoins) {
                            Log.d("Volley: ", "coins deposited");
                            Toast.makeText(getApplicationContext(), "Deposited 5 coins to play.", Toast.LENGTH_SHORT).show();
                            depositingCoins = false;
                        } else {
                            Log.d("Volley: ", "updating highscore");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


}