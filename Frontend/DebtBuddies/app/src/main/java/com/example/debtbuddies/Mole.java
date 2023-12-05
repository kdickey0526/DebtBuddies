package com.example.debtbuddies;

import android.widget.ImageView;

/**
 * Used in Whac-A-Mole. Holds an ImageView (picture of the mole) and a boolean value which holds whether the ImageView has
 * been clicked or not.
 */
public class Mole {
    private ImageView mole;
    private boolean clicked;

    /**
     * Constructs a Mole object based on the ImageView provided. Sets clicked to false initially.
     * @param v the mole icon
     */
    public Mole(ImageView v) {
        clicked = false;
        mole = v;
    }

    /**
     * Returns the ImageView of the Mole object.
     * @return
     */
    public ImageView getMole() {
        return this.mole;
    }

    /**
     * Returns the status of the "clicked" boolean.
     * @return whether the ImageView has been clicked or not
     */
    public boolean isClicked() {
        return this.clicked;
    }

    /**
     * Sets the value of the "clicked" boolean based on the provided argument.
     * @param b true when it's been clicked, false otherwise
     */
    public void setClicked(boolean b) {
        this.clicked = b;
    }
}
