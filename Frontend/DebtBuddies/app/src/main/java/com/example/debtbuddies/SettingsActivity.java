package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/Settings/";
//    private JSONObject settingsProfile;
    private Switch sw_enableSounds;
    private Switch sw_darkMode;
    private boolean dark_enabled = MyApplication.enableDarkMode;
    private boolean sounds_enabled = MyApplication.enableSounds;    // could probably get away with not needing/using this variable but its ok lol

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // instantiate views
        sw_enableSounds = findViewById(R.id.sw_enableSounds);
        sw_darkMode = findViewById(R.id.sw_darkMode);

        // if not logged in as guest, check the user's settings and update the UI elements
        // to reflect their decisions
        if (!MyApplication.loggedInAsGuest) { // get user's settings profile and update everything accordingly
            SERVER_URL += MyApplication.currentUserName; // + "/settings/" + MyApplication.currentUserID; // or whatever the URL actually is in backend
            makeJsonObjReq();
            try {
                sw_enableSounds.setChecked(sounds_enabled); // sounds_enabled will have the value set in the user's profile at this point.
                sw_darkMode.setChecked(dark_enabled);
                // update other UI here

            } catch (Exception e) {
                Log.e(TAG, "Failed updating UI based on the user's settings profile.");
                e.printStackTrace();
            }
        } else {
            // logged in as guest, update the UI based on what's in MyApplication class (class is updated regardless of guest or not btw)

            sw_enableSounds.setChecked(MyApplication.enableSounds);
            sw_darkMode.setChecked(MyApplication.enableDarkMode);
            // other settings here

        }

        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }

        // each option should set the according value in MyApplication class
        // if not logged in as guest, post the new settings to database as well
        sw_enableSounds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {

                // necessary updates for guest and registered user
                sounds_enabled = isEnabled; // will be true if enabled, false otherwise
                MyApplication.enableSounds = isEnabled; // this variable is what's used in the actual games to enable/disable sounds

                if (!MyApplication.loggedInAsGuest) {
                    // update backend with new settings
                    try {
                        makeDiffJsonObjReq();
                    } catch (Exception e) {
                        Log.e(TAG, "Failed updating backend with the new sounds setting");
                        e.printStackTrace();
                    }
                }
            }
        });

        sw_darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
                dark_enabled = isEnabled;
                MyApplication.enableDarkMode = isEnabled;

                if (!MyApplication.loggedInAsGuest) {
                    try {
                        makeDiffJsonObjReq();
                    } catch (Exception e) {
                        Log.e(TAG, "Failed updating backend with the new dark mode setting");
                        e.printStackTrace();
                    }
                }

                if (MyApplication.enableDarkMode) {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
                } else {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });

        // other listeners can go here for other settings
    }

    @Override
    protected void onResume() {
        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }
        super.onResume();
    }

    /**
     * Makes a json object request with the given user information.
     * Server URL should be set to get the current user's settings profile.
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
//                settingsProfile = response; // may not actually work, as seen in JSONArray stuff and login screen

                try {
                    sounds_enabled = response.getBoolean("sound"); // or however its stored in database
                    MyApplication.enableSounds = sounds_enabled;

                    dark_enabled = response.getBoolean("darkmode");
                    MyApplication.enableDarkMode = dark_enabled;
                    // other settings here

                } catch (JSONException e) {
                    Log.e(TAG, "failed getting user's settings");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Displays the error to Logcat.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    /**
     * Makes a json object request with the given user information.
     * Server URL should be set to get the current user's settings profile.
     **/
    private void makeDiffJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());

                try {
                    response.put("sound", sounds_enabled);
                    response.put("darkmode", dark_enabled);
                    putRequest(response);

                } catch (JSONException e) {
                    Log.e(TAG, "failed getting user's settings");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Displays the error to Logcat.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }


    /**
     * Sends a JSONObject to the backend. Updates values based on the currentUser.
     */
    private void putRequest(JSONObject postBody) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    /**
                     * Updates the text field "Coins: " when the response is received.
                     * @param response the response from the backend
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        // don't think i need to update anything really
                        Log.d(TAG, "Updating user settings successful.");
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Sends the volley error to Logcat.
                     * @param error the error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Updating user settings unsuccessful", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    // =============================================================================================================================================
    // updating the database in these callbacks is likely overkill, and may cause some lag if the server is lagging. may be better to just update it
    // whenever the individual settings (switches, buttons, etc.) are updated.
    // =============================================================================================================================================

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (!MyApplication.loggedInAsGuest) {
//            try {
//                settingsProfile.put("soundEnabled", sounds_enabled);
//                // other settings here
//
//                postRequest();
//            } catch (Exception e) {
//                Log.e(TAG, "Error updating settings in database when calling onStop");
//                e.printStackTrace();
//            }
//        }
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (!MyApplication.loggedInAsGuest) {
//            try {
//                settingsProfile.put("soundEnabled", sounds_enabled);
//                // other settings here
//
//                postRequest();
//            } catch (Exception e) {
//                Log.e(TAG, "Error updating settings in database when calling onPause");
//                e.printStackTrace();
//            }
//        }
//    }
}