package com.example.debtbuddies;

import android.app.Application;

import org.json.JSONObject;

public class MyApplication extends Application {
    public static JSONObject currentUser;
    public static boolean loggedInAsGuest = false;
}
