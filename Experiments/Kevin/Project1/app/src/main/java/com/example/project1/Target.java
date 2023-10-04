package com.example.project1;

import android.widget.ImageView;

/**
 * Class modeling a target. Each target has an associated ImageView and a boolean value
 * which specifies if it has been clicked or not yet.
 */
public class Target {

    /**
     * Backing ImageView for a target.
     */
    private ImageView target;
    /**
     * Boolean stating whether a target has been clicked or not.
     */
    private boolean beenClicked;

    /**
     * Constructor for making a Target object.
     * @param t the target ImageView
     */
    public Target(ImageView t) {
        beenClicked = false;
        target = t;
    }

    /**
     * Gets the target ImageView.
     * @return the target ImageView
     */
    public ImageView getTarget() {
        return this.target;
    }

    /**
     * Sets the beenClicked boolean to true.
     */
    public void setClicked(boolean bool) {
        this.beenClicked = bool;
    }

    /**
     * Sets the beenClicked boolean to false.
     */
    public boolean isClicked() {
        return this.beenClicked;
    }



}
