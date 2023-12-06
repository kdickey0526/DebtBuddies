package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Screen which displays the current user's friends list. Displays their
 * name and whether they are online.
 */
public class FriendsListActivity extends AppCompatActivity {

    private TextView listOfFriends;
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/";
    private JSONArray userFriendList = null;
    private JSONObject currentFriend = null;
    private int friendID;
    private static final String TAG = "FriendsListActivity";

    // =============================================================================================================================================
    // not sure how adding a friend wants to be handled, so that part still needs to be implemented
    // =============================================================================================================================================

    /**
     * Runs when the screen is navigated to. Initializes UI elements
     * and fetches the current user's friends.
     * @param savedInstanceState the instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // instantiate views
        listOfFriends = findViewById(R.id.tv_friends);

        if (!MyApplication.loggedInAsGuest) {
            // setup server URL
            SERVER_URL += "getAllFriends/" + MyApplication.currentUserName; // or something similar

            // request user's friends list from backend here
            makeJsonArrayReq();

            // running theory is that receives json object of ID's of each friend, so grab each friend and append data
            // for each loop for each friend in the list/json object
            // display each user adding onto the text view, similar to how global chat was implemented
            // also display the user's online status

//            try {
//                int i = 0;
//                for (i = 0; i < userFriendList.length(); i++) { // .length returns # of key/value pairs in the object
//                    friendID = userFriendList.getJSONObject(i).getInt("person_id"); // getInt("person_id" + i); // not sure how the friends are mapped in the database, should grab the friend's ID though
//
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + friendID;
//                    makeFriendJsonObjReq(); // get that friend's info
//
//                    boolean onlineStatus = currentFriend.getBoolean("is_online"); // not sure what it's actually named in backend
//                    String name = currentFriend.getString("name");
//                    String status = "";
//
//                    if (onlineStatus)
//                        status = "Online";
//                    else
//                        status = "Offline";
//
//                    String previousList = listOfFriends.getText().toString();
//                    listOfFriends.setText(previousList + name + "\t\tStatus: " + status + "\n");
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "The userFriendList JSONObject is probably null.");
//                e.printStackTrace();
//                Log.d(TAG, "userFriendList value = " + userFriendList);
//            }
        } else {
            listOfFriends.setText("Silly goose, you're logged in as guest! You have no friends.");
        }
    }

    /**
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
//                    userFriendList = response; //new JSONArray("{\n\t\"Array\": " + response + "\n}"); // store json object
                    try {
                        int i = 0;
                        for (i = 0; i < response.length(); i++) { // .length returns # of key/value pairs in the object
                            friendID = response.getJSONObject(i).getInt("person_id"); // getInt("person_id" + i); // not sure how the friends are mapped in the database, should grab the friend's ID though

                            SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + friendID;
                            makeFriendJsonObjReq(); // get that friend's info

//                            boolean onlineStatus = currentFriend.getBoolean("is_online"); // not sure what it's actually named in backend
//                            String name = currentFriend.getString("name");
//                            String status = "";
//
//                            if (onlineStatus)
//                                status = "Online";
//                            else
//                                status = "Offline";
//
//                            String previousList = listOfFriends.getText().toString();
//                            listOfFriends.setText(previousList + name + "\t\tStatus: " + status + "\n");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "The userFriendList JSONObject is probably null.");
                        e.printStackTrace();
                        Log.d(TAG, "userFriendList value = " + userFriendList);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed ");
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                    error.printStackTrace();
                }
            });
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }

    /**
     * Makes a json object request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeFriendJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
//                currentFriend = response; // store json object
                try {
                    boolean onlineStatus = response.getBoolean("is_online"); // not sure what it's actually named in backend
                    String name = response.getString("name");
                    String status = "";

                    if (onlineStatus)
                        status = "Online";
                    else
                        status = "Offline";

                    String previousList = listOfFriends.getText().toString();
                    listOfFriends.setText(previousList + name + "\t\tStatus: " + status + "\n");
                } catch (Exception e) {
                    Log.e(TAG, "error getting the object from the array");
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
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
}