package com.example.debtbuddies;

import android.widget.ImageView;

public class Mole {
    private ImageView mole;
    private boolean clicked;

    public Mole(ImageView v) {
        clicked = false;
        mole = v;
    }

    public ImageView getMole() {
        return this.mole;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setClicked(boolean b) {
        this.clicked = b;
    }
}
