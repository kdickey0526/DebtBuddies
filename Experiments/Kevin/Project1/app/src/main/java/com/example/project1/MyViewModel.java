package com.example.project1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Class for the ViewModel object. Used so the app doesn't crash when rotating and for LiveData.
 */
public class MyViewModel extends ViewModel {
    /**
     * Holds the current score of the player.
     */
    public MutableLiveData<Integer> currentScore;

    /**
     * Holds the current runtime/playtime for the round of the game. Reset every new round.
     */
    public MutableLiveData<Long> currentTime;

    /**
     * Gets the current score of the user.
     * @return the current score as an integer
     */
    public MutableLiveData<Integer> getCurrentScore() {
        if (currentScore == null) {
            currentScore = new MutableLiveData<Integer>(0);
        }
        return currentScore;
    }

    /**
     * Gets the current runtime of each round of the game.
     * @return the current playtime of the round
     */
    public MutableLiveData<Long> getCurrentTime() {
        if (currentTime == null) {
            currentTime = new MutableLiveData<Long>();
        }
        return currentTime;
    }
}
