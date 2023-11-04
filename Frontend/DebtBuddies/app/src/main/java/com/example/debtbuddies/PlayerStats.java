package com.example.debtbuddies;

public class PlayerStats {
    int bal, bet;
    boolean win;

    public void gameStats(boolean s, int v, int b) {
        win = s;
        bet = b;
        if (s == true) {
            bal += b;
        } else {
            bal -= b;
        }
    }
}
