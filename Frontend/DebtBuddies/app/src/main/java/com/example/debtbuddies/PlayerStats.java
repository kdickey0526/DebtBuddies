package com.example.debtbuddies;

/**
 * Class representing a player's stats for a particular game.
 * Currently not implemented in games.
 */
public class PlayerStats {
    int bal, bet;
    boolean win;

    /**
     * Initializes a player's stats based on given parameters.
     * @param s boolean holding whether the player won or lost (true or false respectively)
     * @param v the user's bet
     * @param b the user's balance
     */
    public void gameStats(boolean s, int v, int b) {
        win = s;
        bet = v;
        if (s == true) {
            bal += b;
        } else {
            bal -= b;
        }
    }
}
