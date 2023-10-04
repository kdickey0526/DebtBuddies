package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
//import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The main activity for the app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * TAG for Logcat.
     */
    private static final String TAG = "MainActivity";

    /**
     * Reference to the start button in the activity.
     */
    private Button startBtn;

    /**
     * Reference to the handler. Used for posting Messages to the MessageQueue.
     */
    private Handler handler;

    /**
     * Reference to the model. Used for LiveData.
     */
    private MyViewModel model;

    /**
     * Reference to the runnable responsible for the score. Used for LiveData.
     */
    private Runnable scoreRunnable; // probably don't need a runnable for score

    /**
     * Reference to the runnable responsible for spawning targets & such.
     */
    private Runnable gameRunnable;

    /**
     * Reference to the runnable responsible for the time. Used for LiveData.
     */
    private Runnable timeRunnable;

    /**
     * The text on the UI clock/timer.
     */
    private String runningTime = "";

    /**
     * UI element of the clock/timer.
     */
    private TextView clock;

    /**
     * UI element of the score.
     */
    private TextView score;

    /**
     * UI element of high score.
     */
    private TextView UIhighscore;


    /**
     * UI element for current missed.
     */
    private TextView curMissed;

    /**
     * Key string for SharedPreferences.
     */
    private String key_highscore = "key";

    /**
     * The high score.
     */
    private int highscore;

    /**
     * Keeps track of the # of hours passed in the round.
     */
    private long hours;

    /**
     * Keeps track of the # of minutes passed in the round.
     */
    private long minutes;

    /**
     * Keeps track of the # of seconds passed in the round.
     */
    private long seconds;

    /**
     * Array to hold all of the Target objects.
     */
    private Target[] targets;

    /**
     * Long holding the start time of the round.
     */
    private Long startTime;

    /**
     * Holds the number of missed targets per round of the game.
     */
    private int missed = 0;

    /**
     * The constant number of missed targets the player is allowed to miss.
     */
    private final int MAX_MISSED = 3;

    /**
     * Default duration for how long a target stays on screen.
     * 2 seconds.
     */
    private final int DEFAULT_DURATION = 2;

    /**
     * The duration for how long targets will stay visible. If not clicked, will be marked as a miss.
     * By default set to keep targets on screen for 2 seconds.
     */
    private double targetDuration = DEFAULT_DURATION;

    /**
     * Current level that the user is on. Default setup is to increase difficulty of level
     * every 5 levels.
     */
    private int level = 1;

    /**
     * The current target on screen in the game.
     */
    private Target currentTarget;

    /**
     * Boolean variable simply stating whether the game has started or not. Used in target listeners.
     */
    private boolean gameStarted = false;

    /**
     * Current score of user.
     */
    private int curScore = 0;

    /**
     * SharedPreferences handle. Used for storing high score in local storage.
     */
    private SharedPreferences pref;

    /**
     * SharedPreferences editor. Used for editing and setting high score.
     */
    private SharedPreferences.Editor editor;

    /**
     * MediaPlayer for playing sound effects.
     */
    private MediaPlayer mediaPlayer;


    /**
     * Runs when the app is created.
     * @param savedInstanceState saved instance of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.pewpew);

        startBtn = (Button) findViewById(R.id.start_button);
        UIhighscore = (TextView) findViewById(R.id.highscore);
        curMissed = (TextView) findViewById(R.id.curMissed);
        handler = new Handler(getMainLooper());
        scoreRunnable = new Runnable() {
            /**
             * Gets called every time a Message posted by handler is handled.
             * Sets the value of the score.
             */
            @Override
            public void run() {
                // update model.currentScore
                model.currentScore.setValue(curScore);
//                Log.d(TAG, "score updated");
                handler.postDelayed(this, 100); // update score every 100ms
            }
        };
        timeRunnable = new Runnable() {
            /**
             * Gets called every time a Message posted by handler is handled.
             * Sets the new clock time.
             */
            @Override
            public void run() {
                // update model.currentTime based on SystemClock.uptimeMillis()
                model.currentTime.setValue(SystemClock.uptimeMillis() - startTime);

//                Log.d(TAG, "time updated");
                handler.postDelayed(this, 100); // update time every 100ms
            }
        };
        gameRunnable = new Runnable() {
            /**
             * Gets called every time a Message posted by handler is handled.
             * Spawns targets and increments level accordingly.
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
                        targetDuration /= 1.2;
                    }

                    if (!currentTarget.isClicked() && level != 1) { // see if user missed
                        missed++;
                        if (missed > MAX_MISSED) { // make make initial if redundant
                            handler.removeCallbacks(gameRunnable);
                            Log.d(TAG, "runnable: missed too many, ending game");
                            curMissed.setText("You have missed: " + missed + "\nYou can miss: " + MAX_MISSED);
                            resetGame();
                            return;
                        }
                        Log.d(TAG, "runnable: missed target");
                    }

                    currentTarget.getTarget().setVisibility(View.INVISIBLE);
                    currentTarget.setClicked(false);

                    if (level != 1) { // generate new target
                        Random random = new Random();
                        int rand = random.nextInt(12);
                        currentTarget = targets[rand];
                    }

                    level++;
                    curMissed.setText("You have missed: " + missed + "\nYou can miss: " + MAX_MISSED);
                    currentTarget.getTarget().setVisibility(View.VISIBLE);
                    handler.postDelayed(gameRunnable, (long)(targetDuration * 1000));
                }
            }
        };

        // instantiate views, assign target ImageViews to a Target object, store Target objects in an array
        clock = (TextView) findViewById(R.id.runningTime);
        score = (TextView) findViewById(R.id.score);
        UIhighscore = (TextView) findViewById(R.id.highscore);
        targets = new Target[12];
        targets[0] = new Target((ImageView) findViewById(R.id.target1));
        targets[1] = new Target((ImageView) findViewById(R.id.target2));
        targets[2] = new Target((ImageView) findViewById(R.id.target3));
        targets[3] = new Target((ImageView) findViewById(R.id.target4));
        targets[4] = new Target((ImageView) findViewById(R.id.target5));
        targets[5] = new Target((ImageView) findViewById(R.id.target6));
        targets[6] = new Target((ImageView) findViewById(R.id.target7));
        targets[7] = new Target((ImageView) findViewById(R.id.target8));
        targets[8] = new Target((ImageView) findViewById(R.id.target9));
        targets[9] = new Target((ImageView) findViewById(R.id.target10));
        targets[10] = new Target((ImageView) findViewById(R.id.target11));
        targets[11] = new Target((ImageView) findViewById(R.id.target12));




        // set default clock time
        clock.setText("00:00:00");

        // load the high score
        pref = getSharedPreferences(key_highscore, Context.MODE_PRIVATE);
        editor = pref.edit();
//        editor.putInt(key_highscore, highscore);
//        editor.apply();
        highscore = pref.getInt(key_highscore, 0);
        UIhighscore.setText("High Score\n" + highscore);

        curMissed.setText("You have missed: " + missed + "\nYou can miss: " + MAX_MISSED);


        // Get the ViewModel
        model = new ViewModelProvider(this).get(MyViewModel.class);

        // Create observer which updates the UI (score)
        final Observer<Integer> scoreObserver = new Observer<Integer>() {
            /**
             * Updates the score and high score (if necessary) in the UI
             * @param integer  The new score/high score
             */
            @Override
            public void onChanged(Integer integer) {
                // write code to update the UI for score, reset everything if died
                if (missed > MAX_MISSED) { // likely completely unnecessary
                    resetGame();
                } else { // game continues
                    String scoreString = "";
                    scoreString += model.currentScore.getValue();
                    score.setText("Score\n" + scoreString);
                }
            }
        };

        // Create observer which updates the playtime
        final Observer<Long> timeObserver = new Observer<Long>() {
            /**
             * Updates the time of the round in the UI
             * @param t  the current runtime of the round
             */
            @Override
            public void onChanged(Long t) {
                // find the time and format string accordingly
                runningTime = "";
                hours = (t/3600000);
                minutes = ((t - hours*3600000)/60000);
                seconds = ((t - hours*3600000 - minutes*60000)/1000);
                if (hours < 10) {
                    runningTime += "0" + hours;
                } else {
                    runningTime += hours;
                }
                if (minutes < 10) {
                    runningTime += ":0" + minutes;
                } else {
                    runningTime += ":" + minutes;
                }
                if (seconds < 10) {
                    runningTime += ":0" + seconds;
                } else {
                    runningTime += ":" + seconds;
                }
                clock.setText(runningTime); // updates time TextView UI
//                Log.d(TAG, "UI for clock updated");
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentScore().observe(this, scoreObserver);
        model.getCurrentTime().observe(this, timeObserver);
    }

    /**
     * Click listener for Targets.
     * @param v Target object clicked
     */
    public void onTargetClicked(View v) {
        mediaPlayer.start();
        if (gameStarted) {
            // figure out which target v is
            int i;
            for (i = 0; i < 12; i++) {
                if (targets[i].getTarget().equals((ImageView) v)) {
                    v = targets[i].getTarget(); // v is now the ImageView target
                    break;
                }
            }

            if (v.getVisibility() == View.VISIBLE) {
                targets[i].setClicked(true);
                v.setVisibility(View.INVISIBLE);
                curScore++;
                Log.d(TAG, "target clicked, score incremented");
            } else { // shouldn't occur but in case it does (invisible views shouldn't allow listeners to occur)
                missed++;
                Log.d(TAG, "missed target");
            }
        }
    }


    /**
     * Starts the current round of the game.
     *
     * @param view start button
     */
    public void onStartClicked(View view) {
        if (!gameStarted) {
            startTime = SystemClock.uptimeMillis();

            startBtn.setText(R.string.restart);

            // set targets invisible and unclicked
            for (int i = 0; i < targets.length; i++) {
                targets[i].setClicked(false);
                targets[i].getTarget().setVisibility(View.INVISIBLE);
            }

            gameStarted = true;

            handler.postAtTime(scoreRunnable, startTime); // start actively updating score
            handler.postAtTime(timeRunnable, startTime); // start actively updating time

            startGame();

        } else { // game had started and user clicks give up, restarting game
            resetGame();
        }
    }

    /**
     * Starts the game.
     */
    private void startGame() {
        Log.d(TAG, "startGame called");

        // set targets invisible & not clicked
        for (int i = 0; i < targets.length; i++) {
            targets[i].setClicked(false);
            targets[i].getTarget().setVisibility(View.INVISIBLE);
        }

        Random random = new Random();
        int rand = random.nextInt(12);
        currentTarget = targets[rand];

        handler.postDelayed(gameRunnable, 0);
    }

    /**
     * Resets the game.
     */
    private void resetGame() {
        Log.d(TAG, "resetGame called");
        startBtn.setText(R.string.start);

        handler.removeCallbacks(gameRunnable);
        handler.removeCallbacks(scoreRunnable);
        handler.removeCallbacks(timeRunnable);

        // set targets visible & not clicked
        for (int i = 0; i < targets.length; i++) {
            targets[i].setClicked(false);
            targets[i].getTarget().setVisibility(View.VISIBLE);
        }

        gameStarted = false;
        currentTarget = null;
        targetDuration = DEFAULT_DURATION;
        level = 1;
        missed = 0;

        // update high score if necessary
        if (curScore > highscore) {
            highscore = curScore;

            // update local saved data
            editor.putInt(key_highscore, highscore);
            editor.apply();

            // update highscore
            UIhighscore.setText("High Score\n" + highscore);
        }

        curScore = 0;

    }

    /**
     * Called when the app stops.
     */
    @Override
    public void onStop() {
        super.onStop();
        // probably add code to reset the game here
        handler.removeCallbacks(scoreRunnable);
        handler.removeCallbacks(timeRunnable);
        handler.removeCallbacks(gameRunnable);
        resetGame();
    }
}