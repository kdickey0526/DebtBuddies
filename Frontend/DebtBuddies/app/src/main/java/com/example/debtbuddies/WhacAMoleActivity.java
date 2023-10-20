package com.example.debtbuddies;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import org.w3c.dom.Text;

import java.util.Random;

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
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whac_amole);

        mediaPlayer = MediaPlayer.create(this, R.raw.pewpew);

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
                        targetDuration /= 1.1; // 10% harder every level
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
        pref = getSharedPreferences(key_highscore, Context.MODE_PRIVATE);
        editor = pref.edit();
        highscore = pref.getInt(key_highscore, 0);
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

    public void onStartBtnClicked(View view) {
        if (!gameStarted) {
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

    @Override
    public void onBackPressed() {
        // add code here to update database and SharedPreferences (probably call to resetGame)
        resetGame();
        super.onBackPressed();
    }

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

        handler.postDelayed(gameRunnable, 500); // starts game 500ms after clicking start
    }

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
        level = 1;
        missed = 0;

        // update high score if necessary
        if (curScore > highscore) {
            highscore = curScore;

            // update local saved data
            editor.putInt(key_highscore, highscore);
            editor.apply();

            // update highscore
            highscore_ui.setText("High Score\n" + highscore);
        }

        curScore = 0;
    }

    @Override
    public void onStop() {
        resetGame();
        super.onStop();
    }


}