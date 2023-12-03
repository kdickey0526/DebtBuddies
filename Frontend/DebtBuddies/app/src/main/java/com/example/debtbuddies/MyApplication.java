package com.example.debtbuddies;

import android.app.Application;

import org.json.JSONObject;

/**
 * Custom application scope for the whole app. Holds some values to act as global variables.
 */
public class MyApplication extends Application {
    /**
     * Variable holding the JSONObject of the current user logged in. Initially null.
     */
    public static JSONObject currentUser = null;

    /**
     * String holding the current user's username. Makes it easier for setting the server URL.
     */
    public static String currentUserName = null;

    /**
     * Variable holding whether the user has logged in as guest or not.
     */
    public static boolean loggedInAsGuest = false;
}
